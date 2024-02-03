/**
 * Confirmação de exclusão de um contato
 * @author tigo
 * @param idcon
 */

 function confirmar(idcon) {
	 let res = confirm("Confirmar a exclusão deste contato ?");
	 if (res === true) {
		 alert("O contato excluido é representado pelo id: " + idcon);
		 window.location.href=`delete?idcon=${idcon}`;
	 }
 }