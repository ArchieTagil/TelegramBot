package bot.telegram;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;

import org.apache.log4j.Logger;

public class Bot extends TelegramLongPollingBot {
    private int count = 0;
    private final String BOT_TOKEN = "5963626200:AAHbInZwbxQXWc3og47_DB1bOblEpXEtTmI";
    private final String BOT_NAME = "UserNameArchieTagilBot";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
    private SimpleDateFormat sdfJustDate = new SimpleDateFormat("dd.MM.yyyy");
    private Logger logger = Logger.getLogger(Bot.class.getName());

    private FileHandler fh;
    public Bot() throws IOException {
    }

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    public void onUpdateReceived(Update update) {

        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message incomeMessage = update.getMessage();

                String chatId = incomeMessage.getChatId().toString();
                String incomeText = incomeMessage.getText();

                String firstName = incomeMessage.getFrom().getFirstName();
                String secondName = incomeMessage.getFrom().getLastName();
                String userName = incomeMessage.getFrom().getUserName();

                logger.info(sdf.format(new Date()) + "       " + firstName + " " + secondName + " (" + userName + ") " + incomeText);

                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText(parseString(incomeText));

                logger.info(sdf.format(new Date()) + "       " + message.getText());
                message.setReplyMarkup(getInlineKeyBoard());
                execute(message);
            } else if (update.hasCallbackQuery()) {
                String callData = update.getCallbackQuery().getData();
                long chatId = update.getCallbackQuery().getMessage().getChatId();

                String answerText;

                switch (callData) {
                    case "button1":
                        answerText = "???????????? ????????, ??????????????????!";
                        break;
                    case "button2":
                        answerText = "???????????? ????????, ??????????????????!";
                        break;
                    case "button3":
                        answerText = "???????????? ??????????, ??????????????????!";
                        break;
                    case "button4":
                        answerText = "???? ?????????? ??????????????! ?????????? ?????? ?????????????????";
                        break;
                    case "button5":
                        answerText = "???? ???? ?????????? ??????????????! ?? ?????????? ?????? ???????? ?????? ?????????????????";
                        break;
                    case "button6":
                        answerText = "???? ????????????????";
                        break;
                    case "button7":
                        URL weatherUrl = new URL("https://api.openweathermap.org/data/2.5/weather?lat=57.9215&lon=59.9816&units=metric&lang=ru&appid=e686bc3aac52582211db2cd3dd237a91");

                        ObjectMapper weatherMapper = new ObjectMapper();
                        JsonNode weatherNode = weatherMapper.readTree(weatherUrl);

                        StringBuilder weather = new StringBuilder();
                        weather.append("??????????: " + weatherNode.get("name") + "\n");
                        weather.append("????????????: " + weatherNode.get("weather").get(0).get("description") + "\n");
                        weather.append("?????????????????????? ???? ??????????: " + weatherNode.get("main").get("temp") + "\n");
                        weather.append("?????????????????? ??????: " + weatherNode.get("main").get("feels_like") + "\n");
                        weather.append("???????? ??????????: " + weatherNode.get("wind").get("speed") + "??/??");
                        answerText = weather.toString();
                        break;
                    case "button8":
                        URL url = new URL("https://api.bitaps.com/market/v1/ticker/btcusd");
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode node = mapper.readTree(url);
                        String rate = node.get("data").get("last").toString();
                        answerText = "???????? ???????????????? ???? " + sdfJustDate.format(new Date()) + " - " + rate + " ????????????????";
                        break;
                    default:
                        answerText = "???? ???????? ??????????? ?????????????????????? ?????????? ????????!";

                }

                logger.info(sdf.format(new Date()) + "       " + callData + " :: " + answerText);
                SendMessage message = new SendMessage();
                message.setText(answerText);
                message.setChatId(chatId);
                message.setReplyMarkup(getInlineKeyBoard());
                execute(message);
            }
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public String parseString(String incomeText) {
        List<String> randomAnswer = new ArrayList<>();
        randomAnswer.add("^_^");
        randomAnswer.add("??????)))))");
        randomAnswer.add("????????");
        randomAnswer.add("??????????");
        randomAnswer.add("???? ????");
        randomAnswer.add("????????????????");
        randomAnswer.add("?????????? ))))");
        randomAnswer.add("???????????? ??????????");
        randomAnswer.add("?????? ???????? ????????");
        randomAnswer.add("?????????? :-)");
        randomAnswer.add("??????????!");
        randomAnswer.add("??????????????");
        return randomAnswer.get((int) (Math.random() * 11));
    }

    public ReplyKeyboardMarkup getKeyBoard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("????"));
        keyboardRow.add(new KeyboardButton("??????"));
        keyboardRow.add(new KeyboardButton("???? ????????"));
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineKeyBoard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> inlineRow1 = new ArrayList<>();
        List<InlineKeyboardButton> inlineRow2 = new ArrayList<>();
        List<InlineKeyboardButton> inlineRow3 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton("?? ????????! \uD83D\uDC7D");
        button1.setCallbackData("button1");
        InlineKeyboardButton button2 = new InlineKeyboardButton("?? ????????! " + EmojiParser.parseToUnicode(":biohazard:"));
        button2.setCallbackData("button2");
        InlineKeyboardButton button3 = new InlineKeyboardButton("?? ??????????! " + EmojiParser.parseToUnicode(":yin_yang:"));
        button3.setCallbackData("button3");

        InlineKeyboardButton button4 = new InlineKeyboardButton("???? " + EmojiParser.parseToUnicode(":heavy_check_mark:"));
        button4.setCallbackData("button4");
        InlineKeyboardButton button5 = new InlineKeyboardButton("?????? " + EmojiParser.parseToUnicode(":x:"));
        button5.setCallbackData("button5");
        InlineKeyboardButton button6 = new InlineKeyboardButton("???? ????????!");
        button6.setCallbackData("button6");

        InlineKeyboardButton button7 = new InlineKeyboardButton("???????????? ???????????? " + EmojiParser.parseToUnicode(":umbrella:"));
        button7.setCallbackData("button7");
        InlineKeyboardButton button8 = new InlineKeyboardButton("???????????? ???????? ???????????????? " + EmojiParser.parseToUnicode(":heavy_dollar_sign:"));
        button8.setCallbackData("button8");

        inlineRow1.add(button1);
        inlineRow1.add(button2);
        inlineRow1.add(button3);

        inlineRow2.add(button4);
        inlineRow2.add(button5);
        inlineRow2.add(button6);

        inlineRow3.add(button7);
        inlineRow3.add(button8);


        //rows.add(inlineRow1);
        //rows.add(inlineRow2);
        rows.add(inlineRow3);
        markupInline.setKeyboard(rows);
        return markupInline;
    }
}
