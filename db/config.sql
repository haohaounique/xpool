drop table  if exists config_thread;
create table config_thread(
                              id int primary key  auto_increment comment 'id',
                              bean_name varchar(20) not null comment 'bean名称',
                              core_pool_size tinyint default 1 not null comment '核心线程数',
                              max_pool_size  tinyint default 1 not null comment '核心线程数',
                              keep_alive_seconds tinyint default 60 comment '空闲存活时间',
                              queue_capacity tinyint default 50  not null comment '队列大小',
                              rejected_execution_handler varchar(20) default 'abortPolicy' not null comment '拒绝策略',
                              thread_group_name varchar(20) default 1 comment '线程组名称',
                              v_json text comment 'json描述,扩展使用'
);
create unique index idx_bean_name on config_thread(bean_name);
INSERT INTO xk.config_thread ( bean_name, core_pool_size, max_pool_size, keep_alive_seconds, queue_capacity, rejected_execution_handler, thread_group_name, v_json) VALUES ('tuTask', 2, 4, 60, 55, 'abortPolicy', 'message_ad', null);

SELECT * FROM config_thread;