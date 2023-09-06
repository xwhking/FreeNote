package com.xwhking.freenotebackend.model.sentence;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Yiyan {
    private Long id;
    private String uuid;
    private String hitokoto;
    private String type;
    private String from;
    @SerializedName("from_who")
    private String fromWho;
    private String creator;
    private Long creator_uid;
    private Long reviewer;
    @SerializedName("commit_from")
    private String commitFrom;
    @SerializedName("created_at")
    private String createdAt;
    private Long length;
}
