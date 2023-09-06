package com.xwhking.freenotebackend;

import com.google.gson.Gson;
import com.xwhking.freenotebackend.model.entity.Sentences;
import com.xwhking.freenotebackend.model.sentence.Soup;
import com.xwhking.freenotebackend.model.sentence.Yiyan;
import com.xwhking.freenotebackend.model.sentence.getType;
import com.xwhking.freenotebackend.service.SentencesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class TestJsonToObject {
    @Resource
    private SentencesService sentencesService;
    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader("C:\\Users\\28374\\Desktop\\STUDY\\blog\\data\\poem.json");
        Yiyan[] yiyans =  gson.fromJson(fileReader, Yiyan[].class);
        for(Yiyan yiyan : yiyans){
            System.out.println(yiyan);
        }
    }

    /**
     * 处理一言数据保存到数据库
     */
    @Test
    public void saveDataYiyan(){
        File fileDir = new File("C:\\Users\\28374\\Desktop\\STUDY\\blog\\data\\一言一句话\\all");
        File[] files = fileDir.listFiles();
        for(File file : files){
            try {
                FileReader fileReader = new FileReader(file);
                Gson gson = new Gson();
                Yiyan[] yiyans = gson.fromJson(fileReader,Yiyan[].class);
                List<Sentences> sentences = new ArrayList<>();
                for(Yiyan yiyan : yiyans){
                    Sentences sentence = new Sentences();
                    sentence.setType(getType.get(yiyan.getType()));
                    sentence.setSentenceFrom(yiyan.getFrom());
                    sentence.setFromWho(yiyan.getFromWho());
                    sentence.setTimes(yiyan.getReviewer());
                    sentence.setContent(yiyan.getHitokoto());
                    sentences.add(sentence);
                }
                sentencesService.saveBatch(sentences);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * 鸡汤，可能是毒鸡汤
     */
    @Test
    public void saveChickenSoup() throws FileNotFoundException {
        Set<String> sentencesSet = new HashSet<>();
        FileReader fileReader = new FileReader(new File("C:\\Users\\28374\\Desktop\\STUDY\\blog\\data\\一言一句话\\soul.json"));
        Gson gson = new Gson();
        Soup[] soups = gson.fromJson(fileReader,Soup[].class);
        for(Soup soup : soups){
            sentencesSet.add(soup.getContent());
        }
        fileReader = new FileReader(new File("C:\\Users\\28374\\Desktop\\STUDY\\blog\\data\\一言一句话\\鸡汤.json"));
        String[] strs = gson.fromJson(fileReader,String[].class);
        for(String str : strs){
            sentencesSet.add(str);
        }
        List<Sentences> sentences = new ArrayList<>()   ;
        for(String content : sentencesSet){
            Sentences sentence = new Sentences();
            sentence.setContent(content);
            sentence.setType("鸡汤");
            sentences.add(sentence);
        }
        sentencesService.saveBatch(sentences);
    }
}
