
export const IncomeAndExpensesForTheMonth=(xdata,Expenses,Income,profit)=>{
    let option: {
        yAxis: ({ name: string; type: string } | { name: string; type: string })[];
        xAxis: { data: any; type: string }[];
        series: ({ data: any; name: string; type: string } | { data: any; name: string; type: string } | {
            data: any;
            name: string;
            type: string;
            yAxisIndex: number
        })[];
        tooltip: { axisPointer: { crossStyle: { color: string }; type: string }; trigger: string };
        toolbox: {
            feature: {
                saveAsImage: { show: boolean };
                restore: { show: boolean };
                magicType: { show: boolean; type: string[] };
                dataView: { show: boolean; readOnly: boolean }
            }
        }
    };
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        // legend: {
        //   data: ['Evaporation', 'Precipitation', 'Temperature']
        // },
        xAxis: [
            {
                type: 'category',
                data: xdata,
                // axisPointer: {
                //   type: 'shadow'
                // }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '支出',
                // min: 0,
                // max: 250,
                // interval: 50,
                // axisLabel: {
                //   formatter: '{value} 元'
                // }
            },
            {
                type: 'value',
                name: '毛利润',
                // min: 0,
                // max: 25,
                // interval: 5,
                // axisLabel: {
                //   formatter: '{value} 元'
                // }
            }
        ],
        series: [
            {
                name: '支出',
                type: 'bar',
                // tooltip: {
                //   valueFormatter: function (value) {
                //     return value + ' 元';
                //   }
                // },
                // data: [
                //   2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3
                // ]
                data: Expenses

            },
            {
                name: '收入',
                type: 'bar',
                // tooltip: {
                //   valueFormatter: function (value) {
                //     return value + ' 元';
                //   }
                // },
                data: Income
            },
            {
                name: '毛利润',
                type: 'line',
                yAxisIndex: 1,
                // tooltip: {
                //   valueFormatter: function (value) {
                //     return value + ' °C';
                //   }
                // },
                data: profit
            }
        ]
    };
    return option
}