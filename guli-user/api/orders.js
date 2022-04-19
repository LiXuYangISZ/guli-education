import request from '@/utils/request'

export default {
    //1.生成订单
    createOrders(courseId){
        return request({
            url:`/orderservice/order/createOrder/${courseId}`,
            method:'post'
        })
    },
    //2.根据订单id查询订单信息
    getOrderInfo(orderId){
        return request({
            url:`/orderservice/order/getOrderInfo/${orderId}`,
            method:'get'
        })
    },
    //3.生成二维码方法
    createNative(orderNo){
        return request({
            url:`/orderservice/paylog/createNative/${orderNo}`,
            method:'get'
        })
    },

    //4.查询订单状态
    queryPayStatus(orderNo){
        return request({
            url:`/orderservice/paylog/queryPayStatus/${orderNo}`,
            method:'get'
        })
    }


}