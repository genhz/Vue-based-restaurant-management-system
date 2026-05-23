import {getCurrentInstance} from "vue";
import {ECharts, EChartsOption, init} from 'echarts';

// let internalInstance = getCurrentInstance();
// let echarts = internalInstance.appContext.config.globalProperties.$echarts;
export const singleLineChart=(Xdata,Ydata,dom,xlabel,ylabel)=>{
    const myChart:ECharts = init(dom); // 初始化echarts实例
    window.addEventListener('resize', function() {
        myChart.resize();
    });
    const option:EChartsOption = {
        xAxis: {
            type: 'category',
            name:xlabel,
            data: Xdata
        },
        yAxis: {
            type: 'value',
            name:ylabel
        },
        series: [
            {
                data: Ydata,
                type: 'line',
                smooth: true,
                label: {
                    show: true,
                    position: 'bottom'

                }
            }
        ]
    };
    // 设置实例参数
    myChart.setOption(option);
}