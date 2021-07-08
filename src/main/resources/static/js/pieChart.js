console.log(pieData);

let xValues = [];
let yValues = [];
let barColors = [];



function generateColor(){
    let color = '#'+(Math.random() * 0xFFFFFF << 0).toString(16).padStart(6, '0');
    while(true){
        if(!barColors.includes(color))   {
            return color;
        }
    }
}

for (index in pieData) {
    xValues[index] = pieData[index].neighborhood;
    yValues[index] = pieData[index].count;
    barColors[index] = generateColor();
}

console.log(xValues);
console.log(yValues);

new Chart("pieChart", {
    type: "pie",
    data: {
        labels: xValues,
        datasets: [{
            backgroundColor: barColors,
            data: yValues
        }]
    },
    options: {
    }
});