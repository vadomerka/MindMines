package com.example.mindmines.services.converters;

public class ChatMessageTypeConverter {
    public int fromString(String author) {
        switch(author) {
            case "USER":
                return 0;
            case "BOT":
                return 1;
            case "ERROR":
                return 2;
        }
        return 1;
    }

    public boolean isUser(int type) {return type == 0;}
    public boolean isBot(int type) {return type == 1;}
    public boolean isError(int type) {return type == 2;}
}
