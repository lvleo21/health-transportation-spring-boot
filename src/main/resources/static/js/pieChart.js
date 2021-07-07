var xValues = ["São Cristóvão"];
var yValues = [20];
var barColors = [
    "#b91d47",
];0

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