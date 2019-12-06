main.js
```js
import {hasPermission} from './Permission'
Vue.directive('has', {
    bind: function(el, binding){
        Vue.prototype.$hasPermission(binding.value).then(flag =>{
            if(! flag){
                el.parentNode.removeChild(el);
            }
        })
    }
})
```

Permission.js
```js
export const hasPermission = function(permissionSource) {
  return new Promise(resolve =>{
      store.dispatch('getUserPermission').then(() => {
          resolve(checkPermission(permissionSource, store.state.userPermission));
      })
  })
}

let checkPermission = function(permissionSource, userPermission) {
  if(!permissionSource) return !0;
  if(userPermission[`${permissionSource}`]){
      return true;
  }
  return false;
}
```