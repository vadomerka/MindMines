package com.example.mindmines.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmines.R;
import com.example.mindmines.models.chat.ChatMessage;
import com.example.mindmines.services.converters.ChatMessageTypeConverter;

import java.util.List;

import io.noties.markwon.Markwon;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ChatMessage> messages;
    private final Context context;
    private final ChatMessageTypeConverter converter;

    public ChatAdapter(List<ChatMessage> messages, Context context) {
        this.messages = messages;
        this.context = context;
        this.converter = new ChatMessageTypeConverter();
    }

    @Override
    public int getItemViewType(int position) {
        return converter.fromString(messages.get(position).getAuthor());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (converter.isUser(viewType)) {
            View view = inflater.inflate(R.layout.message_item_user, parent, false);
            return new UserMessageViewHolder(view);
        } else if (converter.isError(viewType)) {
            View view = inflater.inflate(R.layout.message_item_error, parent, false);
            return new BotMessageViewHolder(view, context);
        } else {
            View view = inflater.inflate(R.layout.message_item_bot, parent, false);
            return new BotMessageViewHolder(view, context);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        if (holder instanceof UserMessageViewHolder) {
            ((UserMessageViewHolder) holder).bind(message.getBody());
        } else {
            ((BotMessageViewHolder) holder).bind(message.getBody());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // ViewHolder для сообщений пользователя
    private static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        UserMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.message_text);
        }

        void bind(String text) {
            textView.setText(text);
        }
    }

    // ViewHolder для сообщений бота
    private static class BotMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Context context;

        BotMessageViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            textView = itemView.findViewById(R.id.message_text);
            this.context = context;
        }

        void bind(String text) {
            Markwon.create(context).setMarkdown(textView, text);
        }
    }
}
