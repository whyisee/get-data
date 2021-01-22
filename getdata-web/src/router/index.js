import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/login',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '工作看板',
        icon: 'dashboard',
        affix: true }
    }]
  },
  {
    path: '/dataManager',
    component: Layout,
    redirect: '/example/table',
    name: '/DataManager',
    meta: { title: '数据中心', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'getdata',
        name: 'Getdata',
        component: () => import('@/views/getdata/index'),
        meta: { title: '自助取数', icon: 'table' },
        children: [
          {
            path: 'createTask',
            name: 'CreateTask',
            component: () => import('@/views/getdata/createTask/index'),
            meta: { title: '创建取数' }
          },
          {
            path: 'listTask',
            component: () => import('@/views/getdata/taskList/index'),
            name: 'listTask',
            meta: { title: '任务列表' }
          },
          {
            path: 'managerDownload',
            component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
            name: 'ManagerDownload',
            meta: { title: '下载管理' }
          }
        ]

      },
      {
        path: 'usertroop',
        name: 'UserTroop',
        component: () => import('@/views/getdata/index'),
        meta: { title: '用户群管理', icon: 'table' },
        children: [
          {
            path: 'createUserTroop',
            name: 'createUserTroop',
            component: () => import('@/views/getdata/userTroop/createUserTroop/index'),
            meta: { title: '添加用户群' }
          },
          {
            path: 'listUserTroop',
            name: 'listUserTroop',
            component: () => import('@/views/getdata/userTroop/userTroopList/index'),
            meta: { title: '用户群列表' }
          },
          {
            path: 'complaxUserTroop',
            name: 'complaxUserTroop',
            component: () => import('@/views/nested/menu2/index'),
            meta: { title: '用户群集合' }
          }
        ]
      },
      {
        path: 'tagManager',
        name: 'TagManager',
        component: () => import('@/views/getdata/index'),
        meta: { title: '标签管理', icon: 'table' },
        children: [
          {
            path: 'createTask',
            name: 'CreateTask',
            component: () => import('@/views/getdata/tagManager/index'),
            meta: { title: '新增标签' }
          },
          {
            path: 'listTask',
            component: () => import('@/views/getdata/tagManager/tagList/index'),
            name: 'listTask',
            meta: { title: '标签列表' }
          },
          {
            path: 'managerDownload',
            component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
            name: 'ManagerDownload',
            meta: { title: '标签分类' }
          },
          {
            path: 'managerDownload',
            component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
            name: 'ManagerDownload',
            meta: { title: '用户画像' }
          }
        ]

      },
      {
        path: 'dataSource',
        name: 'DataSource',
        component: () => import('@/views/getdata/index'),
        meta: { title: '数据源管理', icon: 'table' },
        children: [
          {
            path: 'createDataSource',
            name: 'createDataSource',
            component: () => import('@/views/nested/menu2/index'),
            meta: { title: '添加数据源' }
          },
          {
            path: 'listDataSource',
            name: 'listDataSource',
            component: () => import('@/views/getdata/dataSource/dataSourceList/index'),
            meta: { title: '数据源列表' }
          }

        ]
      }

    ]
  }

]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  // {
  //   path: '/form',
  //   name: 'Form',
  //   component: () => import('@/views/form/index'),
  //   meta: { title: '任务管理', icon: 'form' },
  //   children: [
  //     {
  //       name: 'xxl-job',
  //       path: 'http://localhost:8080/xxl-job-admin/',
  //       // component: () => import('@/views/nested/menu2/index'),
  //       meta: { title: 'xxl-job' }
  //     },
  //     {
  //       path: 'getDataTask',
  //       name: 'getDataTask',
  //       component: () => import('@/views/nested/menu2/index'),
  //       meta: { title: '取数任务' }
  //     },
  //     {
  //       path: 'etlTask',
  //       name: 'etlTask',
  //       component: () => import('@/views/nested/menu2/index'),
  //       meta: { title: 'ETL任务' }
  //     },
  //     {
  //       path: 'rptTask',
  //       name: 'rptTask',
  //       component: () => import('@/views/nested/menu2/index'),
  //       meta: { title: '报表任务' }
  //     }
  //   ]
  // },
  {
    path: '/doc',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: '文档管理',
      icon: 'nested'
    },
    children: [
      {
        path: 'helpDoc',
        name: 'helpDoc',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: '帮助文档' }
      },
      {
        path: 'shareDoc',
        name: 'shareDoc',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: '知识分享' }
      },
      {
        path: 'fashWeb',
        name: 'fashWeb',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: '优秀站点' }
      }
    ]
  },

  {
    component: Layout,
    path: 'sysManager',
    name: 'sysManager',
    meta: { title: '系统管理', icon: 'link' },

    children: [
      {
        path: 'authManger',
        name: 'authManger',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: '权限管理' }
      },
      {
        path: 'webManager',
        name: 'webManager',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: '页面管理' }
      },
      {
        path: 'backManager',
        name: 'backManager',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: '后台管理' }
      }

    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
