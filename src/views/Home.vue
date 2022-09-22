<template>
	<el-container>
		<el-header class="homeHeader">
			<div class="title">微人事</div>
			<el-dropdown class="userInfo" @command="userHandler">
				<span class="el-dropdown-link">
					{{ user.name }}<i><img :src="user.userface" alt=""></i>
				</span>
				<el-dropdown-menu slot="dropdown">
					<el-dropdown-item command="">个人中心</el-dropdown-item>
					<el-dropdown-item command="settings">设置</el-dropdown-item>
					<el-dropdown-item command="logout" divided>注销</el-dropdown-item>
				</el-dropdown-menu>
			</el-dropdown>
		</el-header>
		<el-container>
			<el-aside width="200px">
				<el-menu router unique-opened>
					<el-submenu v-for="(item, index) in routes" v-if="!item.hidden" :key="index" :index="index+''">
						<template slot="title">
							<i :class="item.iconCls" style="margin: 5px"></i>
							<span>{{ item.name }}</span>
						</template>
						<el-menu-item v-for="(child,indexj) in item.children" :key="indexj" :index="child.path">
							{{ child.name }}
						</el-menu-item>
					</el-submenu>
				</el-menu>
			</el-aside>
			<el-main>
				<el-breadcrumb v-if="this.$router.currentRoute.path!=='/home'" separator-class="el-icon-arrow-right">
					<el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
					<el-breadcrumb-item>{{ this.$router.currentRoute.name }}</el-breadcrumb-item>
				</el-breadcrumb>
				<div v-if="this.$router.currentRoute.path==='/home'" class="homeWelcome">
					欢迎来到微人事!
				</div>
				<router-view class="homeRouterView"/>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
export default {
	name: "Home",
	data() {
		return {
			user: JSON.parse(window.sessionStorage.getItem("user"))
		}
	},
	computed: {
		routes() {
			return this.$store.state.routes;
		}
	},
	methods: {
		userHandler(cmd) {
			if (cmd === "logout") {
				this.$confirm('是否注销?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.getRequest("/logout");
					window.sessionStorage.removeItem("user");
					this.$store.commit("initRoutes", []);
					this.$router.replace("/")
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消注销'
					});
				});
			}
		},
	}
}
</script>

<style>
.homeHeader {
	background-color: #0090ff;
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 0 15px;
	box-sizing: border-box;
}

.homeHeader .title {
	font-family: serif;
	font-size: 30px;
	color: white;
}

.userInfo {
	cursor: pointer;
}

.el-dropdown-link img {
	width: 48px;
	height: 48px;
	border-radius: 24px;
	margin-left: 8px;
}

.el-dropdown-link {
	display: flex;
	align-items: center;
}

.homeWelcome {
	text-align: center;
	color: blue;
	font-size: 30px;
	padding-top: 50px;
	font-family: 微软雅黑;
}

.homeRouterView{
	margin-top: 15px;
}

</style>
