package co.edu.uptc.model;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WordHandler {

    private InputStreamReader reader;
    private final String PATH = "/words.json";
    private Gson gson;
    private Map<String, List<String>> wordList;

    public WordHandler() {
        this.gson = new Gson();
        this.reader = new InputStreamReader(this.getClass().getResourceAsStream(this.PATH));
        this.wordList= this.gson.fromJson(this.reader, new TypeToken <Map<String,List<String>>>(){}.getType());
    }

    public List<String> getWordListDifficulty(Difficulty choosenDifficulty){
     List <String> correspondingList = this.wordList.get(choosenDifficulty.toString());
     return correspondingList;
    }

}
