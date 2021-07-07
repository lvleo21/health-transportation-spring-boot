/*<![CDATA[*/
let tempData = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

for (index in barData) {
    tempData[barData[index].month - 1] = barData[index].count;
}

new Chart("barChart", {
    type: 'bar',
    data: {
        labels: ['Jan.', 'Fev.', 'Mar.', 'Abri.', 'Mai.', 'Jun.', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
        datasets: [{

            data: tempData,
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)',
                'rgba(221,99,255,0.2)',
                'rgba(54,208,235,0.2)',
                'rgba(224,170,104,0.2)',
                'rgba(75,192,174,0.2)',
                'rgba(102,153,255,0.2)',
                'rgba(255,207,64,0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)',
                'rgba(221,99,255, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(224,170,104, 1)',
                'rgba(75,192,174, 1)',
                'rgba(102,153,255, 1)',
                'rgba(255,207,64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        },
        legend: {display: false},

    }
});
/*]]>*/