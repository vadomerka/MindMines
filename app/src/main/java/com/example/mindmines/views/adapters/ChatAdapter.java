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

import java.util.List;

import io.noties.markwon.Markwon;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_USER = 0;
    private static final int TYPE_BOT = 1;
    private List<ChatMessage> messages;
    private Context context;

    public ChatAdapter(List<ChatMessage> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getAuthor().equals("USER") ? TYPE_USER : TYPE_BOT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_USER) {
            View view = inflater.inflate(R.layout.message_item_user, parent, false);
            return new UserMessageViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.message_item_bot, parent, false);
            return new BotMessageViewHolder(view, context);
        }
    }// Привет, я хочу начать делать зарядку по утрам, с чего лучше начать?

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
