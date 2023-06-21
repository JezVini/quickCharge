function SidebarController() {
    var _this = this;
    var sidebar = document.querySelector(".js-atlas-sidebar");
    var mainButtonReference = sidebar.querySelector(".js-create-payment-button")
    
    this.init = function () {
        bindSidebar();
    };
    
    var bindSidebar = function () {
        sidebar.addEventListener("atlas:sidebar-toggle", function () {
            updateMainButtonProps();
        })
    };
    
    var updateMainButtonProps = function() {
        var collapsed = sidebar.collapsed;

        mainButtonReference.setAttribute("description", collapsed ? "" : "Criar Cobrança");
        mainButtonReference.setAttribute("icon", collapsed ? "plus" : "");
        mainButtonReference.toggleAttribute("split", !collapsed);
        mainButtonReference.toggleAttribute("pill", collapsed);
    };

    _this.init();
}

document.addEventListener("DOMContentLoaded", function () {
    const sidebarController = new SidebarController();
});