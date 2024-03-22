package org.example;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, TelegramApiException {
        new MyTelegramBot("Nik_Nasa_bot", new SecretKey().MY_BOT_TOKEN);

    }
}