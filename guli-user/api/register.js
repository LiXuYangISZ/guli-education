import request from '@/utils/request'

export default {
    //发送短信
    sendCode(phone){
        return request({
            url:`/smsservice/sms/sendMsgByGyy/${phone}`,
            method:`get`
        })
    },
    //注册的方法
    registerMember(formItem){
        return request({
            url:`/ucenterservice/member/register`,
            method:`post`,
            data:formItem
        })
    }
}