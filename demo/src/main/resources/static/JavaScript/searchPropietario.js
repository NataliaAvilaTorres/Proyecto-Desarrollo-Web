function buscarPropietarios() {
    // Obtener el valor del campo de búsqueda y convertirlo a minúsculas
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    
    // Obtener todas las filas de la tabla
    const rows = document.querySelectorAll('#customers_table tbody tr');
    
    // Recorrer todas las filas
    rows.forEach(row => {
        // Obtener las celdas de la fila actual
        const cells = row.getElementsByTagName('td');
        
        // Convertir el contenido de las celdas a minúsculas y unirlas en una sola cadena
        const rowText = Array.from(cells).map(cell => cell.textContent.toLowerCase()).join(' ');
        
        // Verificar si el texto de la fila contiene el texto de búsqueda
        if (rowText.includes(searchInput)) {
            // Mostrar la fila si coincide
            row.style.display = '';
        } else {
            // Ocultar la fila si no coincide
            row.style.display = 'none';
        }
    });
}

// Agregar el evento de búsqueda cuando el usuario escriba en el campo de búsqueda
document.getElementById('searchInput').addEventListener('keyup', buscarPropietarios);