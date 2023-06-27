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
        Map actionMap = [
            active : [
                type: "outlined",
                slot: "actions",
                href: createLink(action: 'index', params: attrs.parameterMap + [deletedOnly: false, includeDeleted: false])
            ],
            deleted: [
                type: "outlined",
                slot: "actions",
                href: createLink(action: 'index', params: attrs.parameterMap + [deletedOnly: true, includeDeleted: false])
            ],
            all    : [
                type : "outlined",
                slot : "actions",
                theme: "primary",
                href : createLink(action: 'index', params: attrs.parameterMap + [deletedOnly: false, includeDeleted: true])
            ]
        ]

        if (Boolean.valueOf(attrs.parameterMap.deletedOnly)) {
            actionMap.deleted.type = "filled"
        } else if (Boolean.valueOf(attrs.parameterMap.includeDeleted)) {
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
                tooltip : "Cobrança não recebida",
            ],
            edit         : [
                type    : "filled",
                disabled: "disabled",
                theme   : "secondary",
                icon    : "pencil",
                tooltip : "Cobrança recebida",
            ],
            receiveInCash: [
                type    : "filled",
                disabled: "disabled",
                theme   : "success",
                icon    : "money",
                tooltip : "Cobrança recebida",
            ],
            restore      : [
                type    : "filled",
                disabled: "disabled",
                theme   : "warning",
                icon    : "rotate-dolar",
                tooltip : "Cobrança recebida",
            ],
            delete       : [
                type    : "filled",
                disabled: "disabled",
                theme   : "danger",
                icon    : "trash",
                tooltip : "Cobrança recebida",
            ],
        ]

        if (attrs.payment.status.isReceived()) {

            Map params = [paymentReceiptUniqueId: attrs.payment.getPaymentReceiptUniqueId()]
            actionMap.receipt += [
                tooltip : "Visualizar comprovante",
                href    : createLink(controller: 'paymentReceipt', action: 'show', params: params)
            ]

        } else {

            actionMap.restore.tooltip = "Cobrança não removida"
            actionMap.edit.tooltip = "Cobrança removida"
            actionMap.receiveInCash.tooltip = "Cobrança removida"
            actionMap.delete.tooltip = "Cobrança removida"

            if (attrs.payment.deleted) {

                actionMap.restore += [
                    tooltip : "Restaurar",
                    href    : createLink(action: 'restore', params: attrs.parameterMap)
                ]

            } else if (attrs.payment.status.canUpdate()) {

                actionMap.edit += [
                    tooltip : "Editar",
                    href    : createLink(action: 'edit', params: attrs.parameterMap)
                ]

                actionMap.receiveInCash += [
                    tooltip : "Confirmar recebimento em dinheiro",
                    href    : createLink(action: 'receiveInCash', params: attrs.parameterMap)
                ]

                actionMap.delete += [
                    tooltip : "Remover",
                    href    : createLink(action: 'delete', params: attrs.parameterMap)
                ]
            }
        }

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
                tooltip : "Cliente removido",
            ],
            restore: [
                type    : "filled",
                disabled: "disabled",
                theme   : "warning",
                icon    : "rotate-dolar",
                tooltip : "Cliente não removido",
            ],
            delete : [
                type    : "filled",
                disabled: "disabled",
                theme   : "danger",
                icon    : "trash",
                tooltip : "Cliente removido",
            ],
        ]

        if (attrs.payer.deleted) {

            actionMap.restore += [
                tooltip : "Restaurar",
                href    : createLink(action: 'restore', params: attrs.parameterMap)
            ]

        } else {

            actionMap.edit += [
                tooltip : "Editar",
                href    : createLink(action: 'edit', params: attrs.parameterMap)
            ]

            actionMap.delete += [
                tooltip : "Remover",
                href    : createLink(action: 'delete', params: attrs.parameterMap)
            ]
        }

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
            tooltip : "Primeira página",
            href    : createLink(controller: attrs.controller, action: attrs.action, params: params),
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
            tooltip : "Última página",
            href    : createLink(controller: attrs.controller, action: attrs.action, params: params),
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
