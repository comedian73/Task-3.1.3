const table = document.getElementById('table-info');

table.addEventListener('click', (e) => {
    if (e.target.tagName !== 'BUTTON') return;
    const head = table.tHead.rows[0].cells
    const tr = e.target.closest('tr');
    if (e.target.dataset.type == 'edit') editRow(tr);
});

function editRow(tr) {
    $("#edit-id").attr("value", tr.cells[0].innerText);
    $("#edit-login").attr("value", tr.cells[1].innerText);
    $("#edit-first_name").attr("value", tr.cells[2].innerText);
    $("#edit-last_name").attr("value", tr.cells[3].innerText);
    $("#edit-email").attr("value", tr.cells[4].innerText);
    $("#edit-password").attr("value", tr.cells[5].innerText);

}
