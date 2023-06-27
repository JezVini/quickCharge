document.addEventListener("DOMContentLoaded", function () {
    const alert = document.querySelector(".alert");
    alert.addEventListener("atlas:alert-show", function () {
        if(alert.getAttribute('type') == 'error') return;

        setTimeout(() => {
            alert.destroy();
        }, 5000);
    })
})