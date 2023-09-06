package com.xwhking.freenotebackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 句子
 * @TableName sentences
 */
@TableName(value ="sentences")
@Data
public class Sentences implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 句子主体
     */
    @TableField(value = "content")
    private String content;

    /**
     * 句子类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 句子来由
     */
    @TableField(value = "sentenceFrom")
    private String sentenceFrom;

    /**
     * 句子作者
     */
    @TableField(value = "fromWho")
    private String fromWho;

    /**
     * 调用次数
     */
    @TableField(value = "times")
    private Long times;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "isDelete")
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Sentences other = (Sentences) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getSentenceFrom() == null ? other.getSentenceFrom() == null : this.getSentenceFrom().equals(other.getSentenceFrom()))
            && (this.getFromWho() == null ? other.getFromWho() == null : this.getFromWho().equals(other.getFromWho()))
            && (this.getTimes() == null ? other.getTimes() == null : this.getTimes().equals(other.getTimes()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getSentenceFrom() == null) ? 0 : getSentenceFrom().hashCode());
        result = prime * result + ((getFromWho() == null) ? 0 : getFromWho().hashCode());
        result = prime * result + ((getTimes() == null) ? 0 : getTimes().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", sentenceFrom=").append(sentenceFrom);
        sb.append(", fromWho=").append(fromWho);
        sb.append(", times=").append(times);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}