package kz.proffix4.telegrambot;

import java.io.File;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

class MyTelegramBot extends TelegramLongPollingBot {

    // Метод получения команд бота, тут ничего не трогаем
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText(doCommand(update.getMessage().getChatId(),
                    update.getMessage().getText()));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
            }
        }
    }

    // Тут задается нужное значение имени бота
    @Override
    public String getBotUsername() {
        return "IvanLIRBot";
    }

    // Тут задается нужное значение токена
    @Override
    public String getBotToken() {
        return "985160532:AAHdhUsJ6Yeb5O3thgmHZ1W5d6y4TBXHfvc";
    }

    // Метод обработки команд бота
    public String doCommand(long chatId, String command) {

        if (command.startsWith("/start")) {
            try {
                sendPhoto(new SendPhoto().setChatId(chatId).setNewPhoto(new File("formula.png")));
            } catch (TelegramApiException e) {
            }
            
            String[] param = command.split(" ");
            if (param.length > 1) {
                return "y = " + getANSW(Integer.parseInt(param[1]), Integer.parseInt(param[2]), Integer.parseInt(param[3]));
            } else {
                return "Используйте команду /start и введите переменные a b x!";
            }
        }
        return "Введите команду /start  значения A B X";

    }

    private String getANSW(int a, int b, int x) {
        StringBuilder answ = new StringBuilder();
        double y = 0;
        if (x > 3) {
            y = ((x * ((a * a) + (b * b))) / (6 * a));
        } else {
            y = (x * (1 - (a * b)));
        }
        answ.append(y);
        return answ.toString();

    }
}

public class LIR_TELEGRAM_BOT {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            // ЗАПУСКАЕМ КЛАСС НАШЕГО БОТА
            botsApi.registerBot(new MyTelegramBot());
        } catch (TelegramApiException e) {
        }
    }

}
