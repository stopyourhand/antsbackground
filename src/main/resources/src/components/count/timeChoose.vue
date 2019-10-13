<template>
    <div class="handle">
        <button :class="{'dateChoose':dateChoose==0,'timeBtn':true}" @click="$emit('change-time',0)">今日</button>
        <button v-if="chooseTitle=='发布分析'||chooseTitle=='交易分析'" :class="{'dateChoose':dateChoose==1,'timeBtn':true}" @click="$emit('change-time',1)">昨日</button>
        <button :class="{'dateChoose':dateChoose==2,'timeBtn':true}" @click="$emit('change-time',2)">最近7天</button>
        <button :class="{'dateChoose':dateChoose==3,'timeBtn':true}" @click="$emit('change-time',3)">最近30天</button>
        <span>时间：</span>
        <div class="time">
            <div>
                <VueCtkDateTimePicker v-model="startTime" :no-label="true" :onlyDate="true" :right="true" :minute-interval="30" :format="'YYYY-MM-DD'" color="#5d759d" button-color="rgb(246,146,138)"></VueCtkDateTimePicker>
            </div>
            ~
            <div>
                <VueCtkDateTimePicker v-model="endTime" :no-label="true" :onlyDate="true" :right="true" :minute-interval="30" format="YYYY-MM-DD" color="#5d759d" button-color="rgb(246,146,138)"></VueCtkDateTimePicker>
            </div>
        </div>
        <button class="search">搜索</button>
    </div>
</template>

<script>
import VueCtk from 'vue-ctk-date-time-picker';
export default {
    data(){
        return{
        }
    },
    props:["dateChoose","startTime","endTime","chooseTitle"],
    components:{
        VueCtk,
    }
}
</script>

<style lang="less" scoped>
@import '../../assets/less/define.less';
.handle{
    .timeBtn{
        padding: 11px 20px;
        margin-right: 20px;
        border: 1px solid #999;
        border-radius: 5px;
        font-size: 18px;
        color: #666;
        background-color: #f2f2f2;
        cursor: pointer;
        outline: none;
    }
    .dateChoose{
        border-color: @topicShallowBColor;
        color: #fff;
        background-color: @topicShallowBColor;
    }
    .time{
        display: inline-block;
        width: 400px;
        div{
            display: inline-block;
            width: 180px;
        }
    }
    .search{
        padding: 11px 21px;
        border-radius: 5px;
        border: 1px solid @topicShallowBColor;
        font-size: 18px;
        color: #fff;
        background-color: @topicShallowBColor;
        cursor: pointer;
    }
}

</style>
