import Vue from 'vue'
import VueRouter from 'vue-router'
import MedicList from '@/views/medicList';
import Landing from '@/views/landing';
import Unverified from '@/views/unverified';
import Login from '@/views/authentication/login';
import Signup from '@/views/authentication/signup';
import SignupPatient from '@/views/authentication/signupPatient';
import SignupStaff from '@/views/authentication/signupStaff';
import MedicHome from '@/views/medic/home';


Vue.use(VueRouter)
  const routes = [
  // example
  // TODO: CHECK best behavior for component
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
  {
    path:'/mediclist/:page',
    name:'MedicList',
    component: MedicList
  },
  {
    path:"/unverified",
    name:"Unverified",
    component:Unverified
  },
  {
    path:'/',
    name:'Landing',
    component: Landing
  },{
    path:"/login",
    name:"Login",
    component:Login
  },{
    path:"/signup",
    name:"Signup",
    component:Signup
  },
  {
    path:"/signup/staff",
    name:"SignupStaff",
    component:SignupStaff
  },
  {
    path:"/signup/patient",
    name:"SignupPatient",
    component:SignupPatient
  },
  {
    path:"/staff/home",
    name:"MedicHome",
    component:MedicHome
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
