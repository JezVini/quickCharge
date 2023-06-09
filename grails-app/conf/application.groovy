

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.quickcharge.app.authentication.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.quickcharge.app.authentication.UserRole'
grails.plugin.springsecurity.authority.className = 'com.quickcharge.app.authentication.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/**',             access: ['IS_AUTHENTICATED_FULLY']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

grails.assets.excludes = ["mixins/*.scss"]
grails.plugin.springsecurity.logout.postOnly = false

grails.config.locations = [
    "file:///quickcharge/application.groovy",
]

grails.plugin.springsecurity.successHandler.defaultTargetUrl = "/dashboard/index"
