(window.webpackJsonp=window.webpackJsonp||[]).push([[6],{170:function(Ee,b,e){"use strict";var ye=e(188),$=e(187),ae=e(174),j=e(179),ie=e(171),R=e(172),H=e(71),L=e.n(H),J=e(72),A=e.n(J),V=e(97),C=e.n(V),Q=e(73),P=e.n(Q),w=e(74),F=e.n(w),z=e(57),S=e.n(z),le=e(182),N=e(184),G=e(45),X=e.n(G),U=e(0),r=e.n(U),Z=e(342),_=e(335),k=e(343),q=e(344),ee=e(345),E=e(346),g=e(60),d=e(37),v=e.n(d),y=e(99),I=e.n(y),te=e(186),Ce=e.n(te),K=e(21),ge=e(165),T=e.n(ge),x=e(167),oe,ue,se;function Me(l){var o=Se();return function(){var a=S()(l),n;if(o){var i=S()(this).constructor;n=Reflect.construct(a,arguments,i)}else n=a.apply(this,arguments);return F()(this,n)}}function Se(){if(typeof Reflect=="undefined"||!Reflect.construct||Reflect.construct.sham)return!1;if(typeof Proxy=="function")return!0;try{return Boolean.prototype.valueOf.call(Reflect.construct(Boolean,[],function(){})),!0}catch(l){return!1}}var Oe=function(l){P()(t,l);var o=Me(t);function t(){return L()(this,t),o.apply(this,arguments)}return A()(t,[{key:"render",value:function(){var n=this.props.record;return r.a.createElement(Re,null,r.a.createElement("img",{src:n.imageLarge,alt:n.imageLarge}),r.a.createElement(Be,null,r.a.createElement("h3",null,"\u4E3B\u6F14"),n.casts.split(",").map(function(i){return r.a.createElement(ce,{key:i},i)}),r.a.createElement("h3",null,"\u5BFC\u6F14"),n.directors.split(",").map(function(i){return r.a.createElement(ce,{key:i},i)}),r.a.createElement("h3",null,"\u7B80\u4ECB"),r.a.createElement("p",null,n.summary),n.countries&&r.a.createElement("h3",null,"\u56FD\u5BB6"),n.countries&&r.a.createElement("p",null,n.countries)))}}]),t}(U.Component),ce=x.a.p(oe||(oe=T()([`
  margin-bottom: 4px;
  margin-top: 0;
  padding: 0;
`]))),Re=x.a.div(ue||(ue=T()([`
  display: flex;
  flex-direction: row;
  margin: 12px;
`]))),Be=x.a.div(se||(se=T()([`
  display: flex;
  flex-direction: column;
  margin-left: 20px;
`]))),De=Oe,fe,ve,de;function Pe(l){var o=Ie();return function(){var a=S()(l),n;if(o){var i=S()(this).constructor;n=Reflect.construct(a,arguments,i)}else n=a.apply(this,arguments);return F()(this,n)}}function Ie(){if(typeof Reflect=="undefined"||!Reflect.construct||Reflect.construct.sham)return!1;if(typeof Proxy=="function")return!0;try{return Boolean.prototype.valueOf.call(Reflect.construct(Boolean,[],function(){})),!0}catch(l){return!1}}var Te=function(l){P()(t,l);var o=Pe(t);function t(){return L()(this,t),o.apply(this,arguments)}return A()(t,[{key:"render",value:function(){var n=this.props.movieList;return r.a.createElement(xe,null,n.map(function(i){return r.a.createElement(Le,null,r.a.createElement(Ae,null,r.a.createElement("a",{href:i.url,target:"_blank",rel:"noreferrer"},i.title),r.a.createElement("p",{style:{display:"grid",justifyItems:"end"}},i.rating)),r.a.createElement("img",{src:i.imageLarge,alt:i.imageLarge}))}))}}]),t}(U.Component),xe=x.a.div(fe||(fe=T()([`
  display: grid;
  grid-gap: 1rem;
  grid-template-columns: repeat(auto-fit, minmax(270px, 1fr));
  place-items: center;
`]))),Le=x.a.div(ve||(ve=T()([`
// display: grid;
// place-items: center;
`]))),Ae=x.a.div(de||(de=T()([`
display: grid;
grid-template-columns: repeat(auto-fit, minmax(135px, 1fr));
`]))),Fe=Te;function Ue(l,o){var t=typeof Symbol!="undefined"&&l[Symbol.iterator]||l["@@iterator"];if(!t){if(Array.isArray(l)||(t=Ke(l))||o&&l&&typeof l.length=="number"){t&&(l=t);var a=0,n=function(){};return{s:n,n:function(){return a>=l.length?{done:!0}:{done:!1,value:l[a++]}},e:function(m){throw m},f:n}}throw new TypeError(`Invalid attempt to iterate non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}var i=!0,s=!1,p;return{s:function(){t=t.call(l)},n:function(){var m=t.next();return i=m.done,m},e:function(m){s=!0,p=m},f:function(){try{!i&&t.return!=null&&t.return()}finally{if(s)throw p}}}}function Ke(l,o){if(l){if(typeof l=="string")return me(l,o);var t=Object.prototype.toString.call(l).slice(8,-1);if(t==="Object"&&l.constructor&&(t=l.constructor.name),t==="Map"||t==="Set")return Array.from(l);if(t==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(t))return me(l,o)}}function me(l,o){(o==null||o>l.length)&&(o=l.length);for(var t=0,a=new Array(o);t<o;t++)a[t]=l[t];return a}function We(l){var o=be();return function(){var a=S()(l),n;if(o){var i=S()(this).constructor;n=Reflect.construct(a,arguments,i)}else n=a.apply(this,arguments);return F()(this,n)}}function be(){if(typeof Reflect=="undefined"||!Reflect.construct||Reflect.construct.sham)return!1;if(typeof Proxy=="function")return!0;try{return Boolean.prototype.valueOf.call(Reflect.construct(Boolean,[],function(){})),!0}catch(l){return!1}}var je=Object.defineProperty,ze=function(o,t,a){return t in o?je(o,t,{enumerable:!0,configurable:!0,writable:!0,value:a}):o[t]=a},O=function(o,t,a){return ze(o,X()(t)!=="symbol"?t+"":t,a),a},Ne=N.a.Search;I.a.extend(Ce.a);var Ye=[{text:"\u5267\u60C5",value:"\u5267\u60C5"},{text:"\u52A8\u4F5C",value:"\u52A8\u4F5C"},{text:"\u559C\u5267",value:"\u559C\u5267"},{text:"\u7231\u60C5",value:"\u7231\u60C5"},{text:"\u6218\u4E89",value:"\u6218\u4E89"},{text:"\u7EAA\u5F55\u7247",value:"\u7EAA\u5F55\u7247"},{text:"\u79D1\u5E7B",value:"\u79D1\u5E7B"},{text:"\u52A8\u753B",value:"\u52A8\u753B"}],W="watchedMovieList",ne="starMovieList",pe=function(l){P()(t,l);var o=We(t);function t(){var a;return L()(this,t),a=o.apply(this,arguments),O(C()(a),"state",{current:1,pageSize:6,watchedMovieSet:Object(K.h)(W),starMovieSet:Object(K.h)(ne),searchText:""}),O(C()(a),"onShowSizeChange",function(n,i){a.setState({current:n,pageSize:i})}),O(C()(a),"onChange",function(n){a.setState({current:n})}),O(C()(a),"updateCheck",function(n,i,s){var p=a.props.dispatch;p(K.e.request({id:i,viewed:n})),n?(Object(K.i)(i,s),a.setViewOrStarState(s,i)):(Object(K.a)(i,s),a.setUnviewOrUnstarState(s,i))}),O(C()(a),"setViewOrStarState",function(n,i){var s=a.state,p=s.watchedMovieSet,h=s.starMovieSet;if(n===W){var m=p;m.add(i),a.setState({watchedMovieSet:m})}else{var D=h;D.add(i),a.setState({starMovieSet:D})}}),O(C()(a),"setUnviewOrUnstarState",function(n,i){var s=a.state,p=s.watchedMovieSet,h=s.starMovieSet;if(n===W){var m=p;m.delete(i),a.setState({watchedMovieSet:m})}else{var D=h;D.delete(i),a.setState({starMovieSet:D})}}),O(C()(a),"numberToBoolean",function(n){return n>0}),O(C()(a),"searchMovie",function(n){a.setState({searchText:n})}),O(C()(a),"renderLatestSyncTime",function(){var n=a.props.data;if(n.length===0)return"";var i="",s=Ue(n),p;try{for(s.s();!(p=s.n()).done;){var h=p.value;h.updateTime>i&&(i=h.updateTime)}}catch(m){s.e(m)}finally{s.f()}return"\u4E0A\u6B21\u540C\u6B65\u65F6\u95F4: ".concat(I.a.utc(i).local().format("YYYY-MM-DD HH:mm:ss"))}),a}return A()(t,[{key:"render",value:function(){var n=this,i=this.state,s=i.watchedMovieSet,p=i.starMovieSet,h=i.current,m=i.pageSize,D=[{title:"\u7535\u5F71\u540D\u79F0",key:"name",dataIndex:"title",render:function(c,u){return r.a.createElement("a",{href:u.url,target:"_blank",rel:"noopener noreferrer"},c)},sorter:function(c,u){return c.title.localeCompare(u.title)},width:200},{title:"\u8BC4\u5206",key:"rate",dataIndex:"rating",sorter:{compare:function(c,u){return c.rating-u.rating},multiple:1},width:100},{title:"\u4E0A\u6620\u5E74\u4EFD",key:"year",dataIndex:"movieYear",sorter:{compare:function(c,u){return c.movieYear-u.movieYear},multiple:1},width:150},{title:"\u7C7B\u578B",key:"genres",dataIndex:"genres",width:150,filters:Ye,filterMultiple:!0,onFilter:function(c,u){return u.genres.includes(c)}},{title:"\u5DF2\u770B",key:"viewed",dataIndex:"viewed",render:function(c,u){var M=!1;return u&&u.movieId&&s.has(u.movieId)&&(M=!0),r.a.createElement("span",null,M&&r.a.createElement(R.a,{type:"circle",onClick:function(){n.updateCheck(!M,u.movieId,W)}},r.a.createElement(Z.a,null)),!M&&r.a.createElement(R.a,{type:"circle",onClick:function(){n.updateCheck(!M,u.movieId,W)}},r.a.createElement(_.a,null)))},width:100,filters:[{text:"\u5DF2\u770B",value:1},{text:"\u672A\u770B",value:0}],filterMultiple:!1,onFilter:function(c,u){return u.viewed===n.numberToBoolean(c)}},{title:"\u60F3\u770B",key:"star",dataIndex:"star",render:function(c,u){var M=!1;return u&&u.movieId&&p.has(u.movieId)&&(M=!0),r.a.createElement("span",null,M&&r.a.createElement(R.a,{type:"circle",onClick:function(){n.updateCheck(!M,u.movieId,ne)}},r.a.createElement(k.a,null)),!M&&r.a.createElement(R.a,{type:"circle",onClick:function(){n.updateCheck(!M,u.movieId,ne)}},r.a.createElement(q.a,null)))},width:100,filters:[{text:"\u60F3\u770B",value:1},{text:"\u4E0D\u770B",value:0}],filterMultiple:!1,onFilter:function(c,u){return u.star===n.numberToBoolean(c)}}],Y=this.props,Je=Y.showSyncButton,Ve=Y.showRefreshButton,Qe=Y.syncMovies,we=Y.refreshMovies,B=this.props.data;if(B!==null){var he=this.state.searchText;he!==""&&(B=B.filter(function(f){return f.title.includes(he)})),B.map(function(f,c){f.key=c})}return s&&B.map(function(f){s.has(f.movieId)&&(f.viewed=!0)}),p&&B.map(function(f){p.has(f.movieId)&&(f.star=!0)}),r.a.createElement("div",null,window.outerWidth>=1e3&&r.a.createElement("div",null,r.a.createElement("div",{style:{marginBottom:16,display:"grid",gridTemplateColumns:"5fr 5fr"}},r.a.createElement("div",{style:{display:"grid"}},r.a.createElement(Ne,{placeholder:"\u7535\u5F71\u641C\u7D22",onSearch:this.searchMovie,style:{width:200}})),Je&&r.a.createElement("div",{style:{display:"grid",justifyItems:"end",alignItems:"center"}},r.a.createElement("div",null,r.a.createElement("span",{style:{marginRight:8}},this.renderLatestSyncTime()),r.a.createElement(j.a,{placement:"top",title:"\u540C\u6B65"},r.a.createElement(R.a,{shape:"circle",icon:r.a.createElement(ee.a,null),onClick:Qe})))),Ve&&r.a.createElement("div",{style:{display:"grid",justifyItems:"end",alignItems:"center"}},r.a.createElement(j.a,{placement:"bottom",title:"reload"},r.a.createElement(R.a,{shape:"circle",icon:r.a.createElement(E.a,null),onClick:we})))),r.a.createElement($.a,{columns:D,dataSource:B,pagination:{current:h,pageSize:m,showSizeChanger:!0,onShowSizeChange:this.onShowSizeChange,pageSizeOptions:["6","8","10","15","20"],showQuickJumper:!0,onChange:this.onChange},expandedRowRender:function(c){return r.a.createElement(De,{record:c})},rowKey:function(c){return c.id}})),window.outerWidth<1e3&&r.a.createElement(Fe,{movieList:B}))}}]),t}(r.a.Component);function $e(){return{}}function He(l){return{dispatch:l}}pe.propTypes={showSyncButton:v.a.bool,showRefreshButton:v.a.bool};var Ge=b.a=Object(g.connect)($e,He)(pe)},336:function(Ee,b,e){"use strict";e.r(b);var ye=e(180),$=e(181),ae=e(71),j=e.n(ae),ie=e(72),R=e.n(ie),H=e(97),L=e.n(H),J=e(73),A=e.n(J),V=e(74),C=e.n(V),Q=e(57),P=e.n(Q),w=e(45),F=e.n(w),z=e(0),S=e.n(z),le=e(60),N=e(21),G=e(170);function X(E){var g=U();return function(){var v=P()(E),y;if(g){var I=P()(this).constructor;y=Reflect.construct(v,arguments,I)}else y=v.apply(this,arguments);return C()(this,y)}}function U(){if(typeof Reflect=="undefined"||!Reflect.construct||Reflect.construct.sham)return!1;if(typeof Proxy=="function")return!0;try{return Boolean.prototype.valueOf.call(Reflect.construct(Boolean,[],function(){})),!0}catch(E){return!1}}var r=Object.defineProperty,Z=function(g,d,v){return d in g?r(g,d,{enumerable:!0,configurable:!0,writable:!0,value:v}):g[d]=v},_=function(g,d,v){return Z(g,F()(d)!=="symbol"?d+"":d,v),v},k=function(E){A()(d,E);var g=X(d);function d(){var v;return j()(this,d),v=g.apply(this,arguments),_(L()(v),"onSync",function(){var y=v.props.dispatch;y(Object(N.j)())}),v}return R()(d,[{key:"componentDidMount",value:function(){var y=this.props.dispatch;y(N.c.request())}},{key:"render",value:function(){var y=this.props,I=y.data,te=y.loading;return S.a.createElement("div",null,S.a.createElement($.a,{tip:"Loading...",spinning:te},S.a.createElement(G.a,{data:I,showSyncButton:!0,syncMovies:this.onSync})))}}]),d}(z.Component);function q(E){return{data:E.info.movieRecentList.data,loading:E.info.movieRecentList.loading}}function ee(E){return{dispatch:E}}b.default=Object(le.connect)(q,ee)(k)}}]);
