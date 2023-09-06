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
 * 习惯打卡表
 * @TableName habitrecord
 */
@TableName(value ="habitrecord")
@Data
public class Habitrecord implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 所属习惯Id
     */
    @TableField(value = "habitId")
    private Long habitId;

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

    /**
     * 对一次习惯打卡的简述记录
     */
    @TableField(value = "habitProfile")
    private String habitProfile;

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
        Habitrecord other = (Habitrecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHabitId() == null ? other.getHabitId() == null : this.getHabitId().equals(other.getHabitId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getHabitProfile() == null ? other.getHabitProfile() == null : this.getHabitProfile().equals(other.getHabitProfile()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHabitId() == null) ? 0 : getHabitId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getHabitProfile() == null) ? 0 : getHabitProfile().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", habitId=").append(habitId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", habitProfile=").append(habitProfile);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}