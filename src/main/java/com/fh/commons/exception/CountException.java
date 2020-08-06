package com.fh.commons.exception;
//处理库存异常解决的是超卖问题在service中判断如果两个人同时下单
//判断商品中的库存根据数据库中的锁如果库存不足（库存没有修改成功）抛出库存异常否则
//就代表修改成功（只有一个人可以成功）让事务进行回滚其他人都是失败
public class CountException extends Exception{
    public CountException(String info){
        super(info);
    }

}
