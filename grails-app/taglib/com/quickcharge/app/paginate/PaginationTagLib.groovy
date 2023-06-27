package com.quickcharge.app.paginate

class PaginationTagLib {
    static namespace = "pagination"

    def payment = { Map attrs ->
        Map model = [
            total       : attrs.total,
            action      : "index",
            controller  : "payment",
            paymentList : attrs.paymentList,
            parameterMap: [
                max           : attrs.max,
                offset        : attrs.offset,
                deletedOnly   : attrs.deletedOnly,
                includeDeleted: attrs.includeDeleted,
            ]
        ]

        out << g.render(template: "/payment/templates/list", model: model)
    }

    def payer = { Map attrs ->
        Map model = [
            total       : attrs.total,
            action      : "index",
            controller  : "payer",
            payerList   : attrs.payerList,
            parameterMap: [
                max           : attrs.max,
                offset        : attrs.offset,
                deletedOnly   : attrs.deletedOnly,
                includeDeleted: attrs.includeDeleted,
            ]
        ]

        out << g.render(template: "/payer/templates/list", model: model)
    }

    def payerHeader = { Map attrs ->
        Map model = [actionMap: getPayerHeaderMap(attrs)]
        out << g.render(template: "/shared/templates/pagination/headerActions", model: model)
    }

    def paymentHeader = { Map attrs ->
        Map model = [actionMap: getPaymentHeaderMap(attrs)]
        out << g.render(template: "/shared/templates/pagination/headerActions", model: model)
    }

    def footer = { Map attrs ->
        Map model = [
            action      : attrs.action,
            buttonList  : getButtonList(attrs),
            controller  : attrs.controller,
            parameterMap: attrs.parameterMap,
        ]

        out << g.render(template: "/shared/templates/pagination/footer", model: model)
    }

    def paymentActions = { Map attrs ->
        Map model = [actionMap: getPaymentActionsMap(attrs)]
        out << g.render(template: "/shared/templates/pagination/tableDataActions", model: model)
    }

    def payerActions = { Map attrs ->
        Map model = [actionMap: getPayerActionsMap(attrs)]
        out << g.render(template: "/shared/templates/pagination/tableDataActions", model: model)
    }

    private Map getHeaderActionsMap(Map attrs) {
        Map parameterMap = attrs.parameterMap + [offset: 0]

        Map actionMap = [
            active : [
                type: "outlined",
                slot: "actions",
                href: createLink(action: 'index', params: parameterMap + [deletedOnly: false, includeDeleted: false])
            ],
            deleted: [
                type: "outlined",
                slot: "actions",
                href: createLink(action: 'index', params: parameterMap + [deletedOnly: true, includeDeleted: false])
            ],
            all    : [
                type : "outlined",
                slot : "actions",
                theme: "primary",
                href : createLink(action: 'index', params: parameterMap + [deletedOnly: false, includeDeleted: true])
            ]
        ]

        if (Boolean.valueOf(parameterMap.deletedOnly)) {
            actionMap.deleted.type = "filled"
        } else if (Boolean.valueOf(parameterMap.includeDeleted)) {
            actionMap.all.type = "filled"
        } else {
            actionMap.active.type = "filled"
        }

        return actionMap
    }

    private Map getPaymentHeaderMap(Map attrs) {
        Map actionMap = getHeaderActionsMap(attrs)

        actionMap.active.description = "Ativas"
        actionMap.active.tooltip = "Exibir apenas cobranças ativas"

        actionMap.deleted.description = "Removidas"
        actionMap.deleted.tooltip = "Exibir apenas cobranças removidas"

        actionMap.all.description = "Todas"
        actionMap.all.tooltip = "Exibir todas cobranças"

        return actionMap
    }

    private Map getPayerHeaderMap(Map attrs) {
        Map actionMap = getHeaderActionsMap(attrs)

        actionMap.active.description = "Ativos"
        actionMap.active.tooltip = "Exibir apenas clientes ativos"

        actionMap.deleted.description = "Removidos"
        actionMap.deleted.tooltip = "Exibir apenas clientes removidos"

        actionMap.all.description = "Todos"
        actionMap.all.tooltip = "Exibir todos clientes"

        return actionMap
    }

    private Map getPaymentActionsMap(Map attrs) {
        attrs.parameterMap.id = attrs.payment.id

        Map actionMap = [
            receipt      : [
                type    : "filled",
                disabled: "disabled",
                theme   : "primary",
                icon    : "file-text",
            ],
            edit         : [
                type    : "filled",
                disabled: "disabled",
                theme   : "secondary",
                icon    : "pencil",
            ],
            receiveInCash: [
                type    : "filled",
                disabled: "disabled",
                theme   : "success",
                icon    : "money",
            ],
            restore      : [
                type    : "filled",
                disabled: "disabled",
                theme   : "warning",
                icon    : "rotate-dolar",
            ],
            delete       : [
                type    : "filled",
                disabled: "disabled",
                theme   : "danger",
                icon    : "trash",
            ],
        ]

        if (attrs.payment.status.isReceived()) {
            actionMap.restore.tooltip = "Cobrança recebida"
            actionMap.edit.tooltip = "Cobrança recebida"
            actionMap.receiveInCash.tooltip = "Cobrança recebida"
            actionMap.delete.tooltip = "Cobrança recebida"
            
            Map params = [paymentReceiptUniqueId: attrs.payment.getPaymentReceiptUniqueId()]
            actionMap.receipt += [
                tooltip : "Visualizar comprovante",
                disabled: "",
                href    : createLink(controller: 'paymentReceipt', action: 'show', params: params)
            ]

            return actionMap
        }
        
        if (attrs.payment.deleted) {
            actionMap.receipt.tooltip = "Cobrança removida"
            actionMap.edit.tooltip = "Cobrança removida"
            actionMap.receiveInCash.tooltip = "Cobrança removida"
            actionMap.delete.tooltip = "Cobrança removida"
        
            actionMap.restore += [
                disabled: "",
                tooltip : "Restaurar",
                href    : createLink(action: 'restore', params: attrs.parameterMap)
            ]

            return actionMap
        }

        actionMap.receipt.tooltip = "Cobrança não recebida"
        actionMap.restore.tooltip = "Cobrança não removida"        
        
        actionMap.edit += [
            tooltip : "Editar",
            disabled: "",
            href    : createLink(action: 'edit', params: attrs.parameterMap)
        ]

        actionMap.receiveInCash += [
            tooltip : "Confirmar recebimento em dinheiro",
            disabled: "",
            href    : createLink(action: 'receiveInCash', params: attrs.parameterMap)
        ]

        actionMap.delete += [
            tooltip : "Remover",
            disabled: "",
            href    : createLink(action: 'delete', params: attrs.parameterMap)
        ]

        return actionMap
    }

    private Map getPayerActionsMap(Map attrs) {
        attrs.parameterMap.id = attrs.payer.id

        Map actionMap = [
            edit   : [
                type    : "filled",
                disabled: "disabled",
                theme   : "secondary",
                icon    : "pencil",
            ],
            restore: [
                type    : "filled",
                disabled: "disabled",
                theme   : "warning",
                icon    : "rotate-dolar",
            ],
            delete : [
                type    : "filled",
                disabled: "disabled",
                theme   : "danger",
                icon    : "trash",
            ],
        ]

        if (attrs.payer.deleted) {
            actionMap.edit.tooltip = "Cliente removido"
            actionMap.delete.tooltip = "Cliente removido"
            
            actionMap.restore += [
                tooltip : "Restaurar",
                disabled: "",
                href    : createLink(action: 'restore', params: attrs.parameterMap)
            ]

            return actionMap
        }

        actionMap.restore.tooltip = "Cliente não removido"
        
        actionMap.edit += [
            tooltip : "Editar",
            disabled: "",
            href    : createLink(action: 'edit', params: attrs.parameterMap)
        ]

        actionMap.delete += [
            tooltip : "Remover",
            disabled: "",
            href    : createLink(action: 'delete', params: attrs.parameterMap)
        ]

        return actionMap
    }

    private Map getPaginationStart(Map attrs) {
        Integer newOffSet = attrs.parameterMap.offset - attrs.parameterMap.max
        Map buttonData = [
            type: "filled",
            icon: "arrow-left"
        ]

        if (newOffSet < 0) {
            return [
                tooltip : "Sem mais registros",
                disabled: "disabled",
            ] + buttonData
        }

        Map params = attrs.parameterMap + [offset: 0, max: attrs.max]
        return [
            tooltip: "Primeira página",
            href   : createLink(controller: attrs.controller, action: attrs.action, params: params),
        ] + buttonData
    }

    private Map getPaginationEnd(Map attrs, Integer lastPage) {
        Integer newOffSet = attrs.parameterMap.offset + attrs.parameterMap.max
        Map buttonData = [
            type: "filled",
            icon: "arrow-right"
        ]

        if (newOffSet >= attrs.total) {
            return [
                tooltip : "Sem mais registros",
                disabled: "disabled",
            ] + buttonData
        }

        Map params = attrs.parameterMap + [offset: lastPage * attrs.parameterMap.max, max: attrs.parameterMap.max]
        return [
            tooltip: "Última página",
            href   : createLink(controller: attrs.controller, action: attrs.action, params: params),
        ] + buttonData
    }

    private List<Map> getButtonList(Map attrs) {
        List<Map> buttonList = []

        final Integer numberOfPageOptions = 5
        Integer currentPageIndex = (Integer) (attrs.parameterMap.offset / attrs.parameterMap.max)
        Integer totalPagesCount = (Integer) (attrs.total / attrs.parameterMap.max) + (attrs.total % attrs.parameterMap.max == 0 ? 0 : 1)

        List<Integer> pageToShowList = []
        Integer startPage = 0, endPage = 0
        if ((currentPageIndex - 2) > 0) {
            if ((currentPageIndex + 2) < totalPagesCount) {
                startPage = currentPageIndex - 2
                endPage = currentPageIndex + 3
            } else {
                startPage = Math.max(0, totalPagesCount - numberOfPageOptions)
                endPage = totalPagesCount
            }
        } else {
            endPage = Math.min(numberOfPageOptions, totalPagesCount)
        }

        for (Integer pageIndex = startPage; pageIndex < endPage; pageIndex++) {
            pageToShowList.add(pageIndex)
        }

        for (Integer page : pageToShowList) {
            Map params = attrs.parameterMap +
                [offset: attrs.parameterMap.max * page, max: attrs.parameterMap.max]
            Map pageData = [
                href  : createLink(controller: attrs.controller, action: attrs.action, params: params),
                number: page + 1,
            ]

            if (page == currentPageIndex) {
                pageData += [
                    type : "filled",
                    class: "current page",
                ]
            } else {
                pageData += [
                    type : "outlined",
                    class: "page",
                ]
            }

            buttonList.add(pageData)
        }

        if (totalPagesCount > numberOfPageOptions) {
            buttonList = [getPaginationStart(attrs)] + buttonList + [getPaginationEnd(attrs, totalPagesCount - 1)]
        }

        return buttonList
    }
}
