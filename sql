create table sentences
(
    id           bigint auto_increment comment 'id'
        primary key,
    content      varchar(1024)                      not null comment '句子主体',
    type         varchar(32)                        not null comment '句子类型',
    sentenceFrom varchar(256)                       null comment '句子来由',
    fromWho      varchar(1024)                      null comment '句子作者',
    times        bigint   default 0                 null comment '调用次数',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除'
)
    comment '句子' collate = utf8mb4_unicode_ci;

create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    email        varchar(256)                                 not null comment '邮箱',
    userPassword varchar(512)                                 not null comment '密码',
    userName     varchar(256)                                 null comment '用户昵称',
    userAvatar   varchar(1024)                                null comment '用户头像',
    userProfile  varchar(512) default '这个人很懒，什么也没写' null comment '用户简介',
    userRole     int          default 0                       not null comment '用户角色：0普通用户，1管理员',
    createTime   datetime     default CURRENT_TIMESTAMP       not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP       not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                       not null comment '是否删除'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create table habit
(
    id           bigint auto_increment comment 'id'
        primary key,
    userId       bigint                             not null comment '所属用户Id',
    title        varchar(256)                       not null comment '标题',
    habitProfile varchar(512)                       null comment '简述',
    endTime      datetime default CURRENT_TIMESTAMP not null comment '习惯结束时间',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    constraint habit___fk
        foreign key (userId) references user (id)
            on delete cascade
)
    comment '习惯表' collate = utf8mb4_unicode_ci;

create table habitrecord
(
    id           bigint auto_increment comment 'id'
        primary key,
    habitId      bigint                             not null comment '所属习惯Id',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    habitProfile varchar(512)                       null comment '对一次习惯打卡的简述记录',
    constraint habitrecord___fk
        foreign key (habitId) references habit (id)
            on delete cascade
)
    comment '习惯打卡表' collate = utf8mb4_unicode_ci;

create table tabs
(
    id         bigint auto_increment comment 'id'
        primary key,
    title      varchar(256)                            not null comment 'tab标题',
    userId     bigint                                  not null comment '创建者Id',
    tabProfile varchar(1024) default '很懒，什么也没说' null comment '标签描述',
    createTime datetime      default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint       default 0                 not null comment '是否删除',
    constraint tabs___fk
        foreign key (userId) references user (id)
            on delete cascade
)
    comment '标签' collate = utf8mb4_unicode_ci;

create table tab_task
(
    id         bigint auto_increment comment 'id'
        primary key,
    tabId      bigint                                  not null comment '标签Id',
    title      varchar(256)                            not null comment '标签任务主题',
    tabProfile varchar(1024) default '很懒，什么也没说' null comment '标签任务描述',
    createTime datetime      default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint       default 0                 not null comment '是否删除',
    isFinish   int           default 0                 null comment '是否完成 0 - 未完成， 1 - 完成',
    constraint tab_task___fk
        foreign key (tabId) references tabs (id)
            on delete cascade
)
    comment '标签栏下的任务' collate = utf8mb4_unicode_ci;

create table target
(
    id         bigint auto_increment comment 'id'
        primary key,
    title      varchar(256)                       not null comment '目标标题',
    userId     bigint                             not null comment '创建者Id',
    content    text                               null comment 'md格式内容，存储对目标规划的具体策划与计划',
    endTime    datetime                           null comment '结束时间，默认不添加时间',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    isFinish   int      default 0                 null comment '是否完成 0 - 未完成， 1 - 完成 ',
    constraint target___fk
        foreign key (userId) references user (id)
            on delete cascade
)
    comment '目标规划' collate = utf8mb4_unicode_ci;

create table todo
(
    id          bigint auto_increment comment 'id'
        primary key,
    userId      bigint                             not null comment '所属用户Id',
    title       varchar(256)                       not null comment 'todo标题',
    todoProfile varchar(512)                       null comment 'todo简述',
    isFinish    int      default 0                 not null comment '是否完成 0 - 未完成 ，  1 - 完成',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除',
    constraint todo___fk
        foreign key (userId) references user (id)
            on delete cascade
)
    comment 'todo表,意在每天当下要做的事情' collate = utf8mb4_unicode_ci;

