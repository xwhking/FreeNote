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
 * 目标规划
 * @TableName target
 */
@TableName(value ="target")
@Data
public class Target implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 目标标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 创建者Id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * md格式内容，存储对目标规划的具体策划与计划
     */
    @TableField(value = "content")
    private String content;

    /**
     * 结束时间，默认不添加时间
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

    /**
     * 是否完成 0 - 未完成， 1 - 完成 
     */
    @TableField(value = "isFinish")
    private Integer isFinish;

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
        Target other = (Target) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getIsFinish() == null ? other.getIsFinish() == null : this.getIsFinish().equals(other.getIsFinish()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getIsFinish() == null) ? 0 : getIsFinish().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", userId=").append(userId);
        sb.append(", content=").append(content);
        sb.append(", endTime=").append(endTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", isFinish=").append(isFinish);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}