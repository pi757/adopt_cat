Vue.component('top-side', {
    template: `<div id="topSide" style="height: 100%">
    <el-container style=" border: 1px solid #eee">
        <el-aside width="200px" style="background-color: rgb(238,241,246)">
            <el-menu :default-openeds="['1']">
                <el-submenu index="1">
                    <template slot="title"><i class="el-icon-search"></i>逛一逛</template>
                    <el-menu-item-group>
                        <el-menu-item index="1-1" @click.native="index">领养</el-menu-item>
                    </el-menu-item-group>
                        <el-menu-item index="1-2" @click.native="toLookCat">寻猫启事</el-menu-item>
                    <el-menu-item-group>
                        <el-menu-item index="1-3" @click.native="toLookArticle">小知识</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
                <el-submenu index="2" >
                    <template slot="title"><i class="el-icon-edit-outline"></i>发布</template>
                    <el-menu-item-group>
                        <el-menu-item index="2-1" @click.native="toFindPeople" :disabled="isLogin">求领养</el-menu-item>
                    </el-menu-item-group>
                        <el-menu-item index="2-2" @click.native="toFindCat" :disabled="isLogin">寻猫</el-menu-item>
                    <el-menu-item-group>
                        <el-menu-item index="2-3" @click.native="toArticle" :disabled="isLogin">分享小知识</el-menu-item>
                    </el-menu-item-group>
                </el-submenu>
            </el-menu>
        </el-aside>

        <el-container>
            <el-header style="text-align: right; font-size: 12px">
            
                <div v-if="user">
                    <el-menu v-if="userId == '2020000001'" style="width: 100%" background-color="#B3C0D1"
                     :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                      <el-menu-item index="1" @click.native="toCheck">审核</el-menu-item>
                      <el-menu-item index="2" @click.native="toManage">用户管理</el-menu-item>
                      <el-menu-item index="3" @click.native="toUpdatePwd">修改密码</el-menu-item>
                        <el-dropdown>
                            <div class="demo-basic--circle" style="margin-top: 10px;margin-right: 15px">
                                <div class="block"><el-avatar :size="35" :src="user.userImage"></el-avatar></div>
                            </div>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item @click.native="toInfo">个人信息</el-dropdown-item>
                                <el-dropdown-item @click.native="toMyArticle">发表的文章</el-dropdown-item>
                                <el-dropdown-item @click.native="toUpdatePwd">修改密码</el-dropdown-item>
                                <el-dropdown-item @click.native="exit()">退出</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    <span>{{user.username}}</span>
                    </el-menu>
                    <div v-else>
                        <el-dropdown >
                                <div class="demo-basic--circle" style="margin-top: 10px;margin-right: 15px">
                                    <div class="block"><el-avatar :size="35" :src="user.userImage"></el-avatar></div>
                                </div>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item @click.native="toInfo">个人信息</el-dropdown-item>
                                    <el-dropdown-item @click.native="toMyArticle">发表的文章</el-dropdown-item>
                                    <el-dropdown-item @click.native="toUpdatePwd">修改密码</el-dropdown-item>
                                    <el-dropdown-item @click.native="exit()">退出</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        <span>{{user.username}}</span></div>
                   
                    </div>
                <div v-else>
                    <el-dropdown>
                        <i class="el-icon-user" style="margin-right: 15px"></i>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="toLogin">登录</el-dropdown-item>
                            <el-dropdown-item @click.native="toRegisterUser">注册</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <span>游客</span>
                </div>
            </el-header>

            <el-main>
                <slot></slot>
            </el-main>
        </el-container>
    </el-container>

</div>`,
    data() {
        return {
            user: JSON.parse(sessionStorage.getItem("user")),
            isLogin: JSON.parse(sessionStorage.getItem("user")) == null,
            userId: JSON.parse(sessionStorage.getItem("user")) == null ? '' : JSON.parse(sessionStorage.getItem("user")).userId,
            activeIndex: '',
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        exit: function () {
            sessionStorage.clear();
            location.href = '/logout';
        },
        toLogin: function () {
            window.location.href = '/l1/toLogin'
        },
        toRegisterUser: function () {
            window.location.href = '/l1/toRegisterUser'
        },
        toFindCat: function () {
            if (this.isLogin) {
                this.$message({
                    showClose: true,
                    message: '你还没有登录，点击右上角去登录',
                    type: 'warning'
                });
            } else {
                window.location.href = '/l2/toFindCat'
            }
        },
        toFindPeople: function () {
            if (this.isLogin) {
                this.$message({
                    showClose: true,
                    message: '你还没有登录，点击右上角去登录',
                    type: 'warning'
                });
            } else {
                window.location.href = '/l2/toFindPeople'
            }
        },
        toArticle: function () {
            if (this.isLogin) {
                this.$message({
                    showClose: true,
                    message: '你还没有登录，点击右上角去登录',
                    type: 'warning'
                });
            } else {
                window.location.href = '/l2/toArticle'
            }
        },
        toLookArticle: function () {
            window.location.href = '/l1/toLookArticle'
        },
        toLookCat: function () {
            window.location.href = '/l1/toLookCat'
        },
        index: function () {
            window.location.href = '/'
        },
        toInfo: function () {
            window.location.href = '/l2/toInfo'
        },
        toMyArticle: function () {
            window.location.href = '/l2/toMyArticle'
        },
        toUpdatePwd: function () {
            window.location.href = '/l2/toUpdatePwd'
        },
        toCheck: function () {
            window.location.href = '/l3/toCheck'
        },
        toManage: function () {
            window.location.href = '/l3/toAllUser'
        },

    },
    mounted: function () {
        var provinces = sessionStorage.getItem("provinces")
        if (provinces == null || provinces.length === 0) {
            axios.get("/l1/provinces").then(result => {
                sessionStorage.setItem("provinces", JSON.stringify(result.data));
            })
        }
    },
})
