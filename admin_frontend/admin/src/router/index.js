import Vue from 'vue'
import Router from 'vue-router'
import Login from '../pages/Login'
import ChangePassword from '../pages/ChangePassword'
import Index from '../pages/Index'
import SchoolCard from '../components/SchoolCard'
import Party from '../components/Party'
import Post from '../components/Post'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/changepsw',
      name: 'changePsw',
      component: ChangePassword
    },
    {
      path: '/index',
      name: 'Index',
      component: Index,
      children: [
        {
          path: 'schoolcard',
          component: SchoolCard
        },
        {
          path: 'party',
          component: Party
        },
        {
          path: 'post',
          component: Post
        },
      ]
    }
  ]
})
