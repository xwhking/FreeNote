package com.xwhking.freenotebackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 习惯表
 * @TableName habit
 */
@TableName(value ="habit")
@Data
public class Habit implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 所属用户Id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 简述
     */
    @TableField(value = "habitProfile")
    private String habitProfile;

    /**
     * 习惯结束时间
     */
    @TableField(value = "endTime")
    private LocalDate endTime;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private LocalDate createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private LocalDate updateTime;

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
        Habit other = (Habit) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getHabitProfile() == null ? other.getHabitProfile() == null : this.getHabitProfile().equals(other.getHabitProfile()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getHabitProfile() == null) ? 0 : getHabitProfile().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", userId=" + userId +
                ", title=" + title +
                ", habitProfile=" + habitProfile +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                ", serialVersionUID=" + serialVersionUID +
                "]";
        return sb;
    }
}