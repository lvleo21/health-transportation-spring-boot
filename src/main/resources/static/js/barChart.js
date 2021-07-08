/*<![CDATA[*/
let tempData = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
let colors = [];

function generateColor(){
    let color = '#'+(Math.random() * 0xFFFFFF << 0).toString(16).padStart(6, '0');
    while(true){
        if(!colors.includes(color))   {
            return color;
        }
    }
}

function loadColors(qnt){
    for (let i = 0; i<qnt; i++){
        colors[i] = generateColor();
    }
}

loadColors(12);

for (index in barData) {
    tempData[barData[index].month - 1] = barData[index].count;
}

new Chart("barChart", {
    type: 'bar',
    data: {
        labels: ['Jan.', 'Fev.', 'Mar.', 'Abri.', 'Mai.', 'Jun.', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
        datasets: [{
            data: tempData,
            backgroundColor: colors,
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