<template>
  <div>

    <!-- 阿里云视频播放器样式 -->
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css" >
    <!-- 阿里云视频播放器脚本 -->
    <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js" />

    <!-- 定义播放器dom -->
    <div id="J_prismPlayer" class="prism-player" />
  </div>
</template>

<script>
import vod from '@/api/vod'

export default{
    layout:'video',//应用video布局
     /**
     * 页面渲染完成时：此时js脚本已加载，Aliplayer已定义，可以使用
     * 如果在created生命周期函数中使用，Aliplayer is not defined错误
     */
    // 因为刚进入页面时,this.vid和this.playAuth都还没有,发送完异步请求后才有.所以视频播放的相关配置应该放在mounted,等页面加载完成后执行
    mounted() {
        new Aliplayer({
        id: 'J_prismPlayer',
        vid: this.vid, // 视频id
        playauth: this.playAuth, // 播放凭证
        encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
        width: '100%',
        height: '740px',
        // 以下可选设置
        cover: 'http://guli.shop/photo/banner/1525939573202.jpg', // 封面
        qualitySort: 'asc', // 清晰度排序

        mediaType: 'video', // 返回音频还是视频
        autoplay: false, // 自动播放
        isLive: false, // 直播
        rePlay: false, // 循环播放
        preload: true,
        controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
        useH5Prism: true, // 播放器类型：html5
        }, function(player) {
            console.log('播放器创建成功')
        })
    },
    asyncData({params, error}) {
        //注意 这里的params.vid 是写页面的名字,如果页面写的是_id,则要写params.id
        return vod.getPlayAuth(params.vid).then(response=>{
            return {
                vid:params.vid,
                playAuth:response.data.data.playAuth
            }
        })
    },
}
</script>
