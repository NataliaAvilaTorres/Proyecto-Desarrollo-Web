function buscarMascotas() {
    // Obtener el valor del input de búsqueda
    let input = document.getElementById('searchInput').value.toLowerCase();
    // Obtener todas las filas de la tabla
    let table = document.getElementById('mascotasTable');
    let tr = table.getElementsByTagName('tr');

    // Comenzamos desde la fila 1, ya que la fila 0 es la cabecera
    for (let i = 1; i < tr.length; i++) {
        let td = tr[i].getElementsByTagName('td');
        let encontrado = false;

        // Revisar cada columna de la fila
        for (let j = 0; j < td.length; j++) {
            if (td[j]) {
                let textValue = td[j].textContent || td[j].innerText;
                if (textValue.toLowerCase().indexOf(input) > -1) {
                    encontrado = true;
                    break;
                }
            }
        }

        // Mostrar u ocultar la fila dependiendo si coincide
        if (encontrado) {
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";
        }
    }
}