package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyTelegramBot extends TelegramLongPollingBot {

    final String BOT_NAME;
    final String BOT_TOKEN;

    String apiKey = new SecretKey().MY_API_KEY;
    final String NASA_URL = "https://api.nasa.gov/planetary/apod?api_key=" + apiKey;

    public MyTelegramBot(String BOT_NAME, String BOT_TOKEN) throws TelegramApiException {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String[] separetedAction = update.getMessage().getText().split(" ");
            String action = separetedAction[0];
            long chatId = update.getMessage().getChatId();

            switch (action) {
                case "/start":
                    sendMessage("Привет! Я бот, созданный в учебных целях. Нажми /help, чтоб узнать что я умею", chatId);
                    break;
                case "/help":
                    sendMessage("Я бот, который отправляет каждый день новую картинку с сайта NASA", chatId);
                    sendMessage("Напиши /image для получения картинки дня", chatId);
                    sendMessage("Для получения картинки за предыдущие дни, напиши: /date YYYY-MM-DD", chatId);
                    sendMessage("Пример:  /date 2023-12-10", chatId);
                    break;
                case "/image":
                    String imageUrl = Utils.getUrl(NASA_URL);
                    sendMessage(imageUrl, chatId);
                    break;
                case "/date":
                    String date = separetedAction[1];
                    imageUrl = Utils.getUrl(NASA_URL + "&date=" + date);
                    sendMessage(imageUrl, chatId);
                    break;
                default:
                    sendMessage("Я тебя не понимаю!", chatId);
                    break;
            }
        }
    }

    void sendMessage(String text, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Сообщение не отправилось");
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}


