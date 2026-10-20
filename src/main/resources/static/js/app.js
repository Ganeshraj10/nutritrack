(function(){
  const sidebar=document.getElementById('sidebar');
  document.querySelectorAll('[data-menu-toggle]').forEach(button=>button.addEventListener('click',()=>sidebar?.classList.toggle('open')));
  document.addEventListener('click',event=>{if(window.innerWidth<=900 && sidebar && sidebar.classList.contains('open') && !sidebar.contains(event.target) && !event.target.closest('[data-menu-toggle]')) sidebar.classList.remove('open');});
  document.querySelectorAll('[data-dismiss]').forEach(button=>button.addEventListener('click',()=>button.closest('.flash')?.remove()));
  document.querySelectorAll('[data-confirm]').forEach(button=>button.addEventListener('click',event=>{const message=button.getAttribute('data-confirm')||'Are you sure?';if(!window.confirm(message))event.preventDefault();}));
  document.querySelectorAll('[data-set-water]').forEach(button=>button.addEventListener('click',()=>{const form=button.closest('form');const input=form?.querySelector('input[name="amountMl"]');if(input){input.value=button.getAttribute('data-set-water');input.form?.requestSubmit();}}));
})();
