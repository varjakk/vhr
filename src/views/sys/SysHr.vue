<template>
	<div>
		<div style="display: flex; justify-content: center">
			<el-input v-model="searchInput" placeholder="搜索" prefix-icon="el-icon-search"
			          style="width: 500px; margin-right: 5px" @keydown.enter.native="search"></el-input>
			<el-button prefix-icon="el-icon-search" type="primary" @click="search">搜索
			</el-button>
		</div>
		<div class="hr-container">
			<el-card class="hr-card" v-model="hrs" :key="index" v-for="(hr,index) in hrs">
				<div slot="header" class="clearfix">
					<span>{{ hr.name }}</span>
					<el-button style="float: right; padding: 3px 0; color: red" type="text"
					           icon="el-icon-delete" @click="deleteHr(hr)"></el-button>
				</div>
				<div>
					<div class="img-container">
						<img class="userface-img" :src="hr.userface" :title="hr.name">
					</div>
					<div class="userinfo-container">
						<div>用户名：{{ hr.name }}</div>
						<div>手机号：{{ hr.phone }}</div>
						<div>电话号：{{ hr.telephone }}</div>
						<div>地址：{{ hr.address }}</div>
						<div>用户状态：
							<el-switch
								v-model="hr.enabled"
								active-color="#13ce66"
								inactive-color="#ff4949"
								active-text="启用"
								inactive-text="禁用"
								@change="statusChange(hr)">
							</el-switch>
						</div>
						<div>用户角色：
							<el-tag v-for="(role, indexj) in hr.roles" :key="indexj" style="margin-right: 4px"
							        type="success">{{ role.nameZh }}
							</el-tag>
							<el-popover
								@show="showPop(hr)"
								@hide="hidePop(hr)"
								placement="right"
								title="角色列表"
								width="200"
								trigger="click">
								<el-select v-model="selectedRoles" placeholder="请选择" multiple>
									<el-option
										v-for="(r,indexk) in allRoles"
										:key="indexk"
										:label="r.nameZh"
										:value="r.id">
									</el-option>
								</el-select>
								<el-button slot="reference" icon="el-icon-more" type="text"></el-button>
							</el-popover>
						</div>
						<div>备注：{{ hr.remark }}</div>
					</div>
				</div>
			</el-card>
		</div>
	</div>
</template>

<script>
export default {
	name: "SysHr",
	data() {
		return {
			searchInput: '',
			hrs: [],
			allRoles: [],
			selectedRoles: [],
		}
	},
	mounted() {
		this.initHrs()
	},
	methods: {
		initHrs() {
			this.getRequest("/system/hr/?keywords=" + this.searchInput).then(resp => {
				if (resp) {
					this.hrs = resp
				}
			})
		},
		initAllRoles() {
			this.getRequest("/system/hr/roles").then(resp => {
				if (resp) {
					this.allRoles = resp
				}
			})
		},
		hidePop(hr) {
			let flag = true//判断选择后的角色id数组与原有的id数组是否相同，只有在不同的时候才进行更新请求
			let roles = hr.roles;
			if (roles.length !== this.selectedRoles.length) {
				flag = false
			} else {
				let sort = this.selectedRoles.sort();
				for (let i = 0; i < roles.length; i++) {
					if (roles[i].id !== sort[i]) {
						flag = false
					}
				}
			}

			if (!flag) {
				let url = '/system/hr/hrrole?hrid=' + hr.id
				this.selectedRoles.forEach(selected => {
					url += '&rids=' + selected
				})
				this.putRequest(url).then(resp => {
					if (resp) {
						this.initHrs()
					}
				})
			}
		},
		statusChange(hr) {
			console.log(hr)
			delete hr.roles
			this.putRequest("/system/hr/", hr).then(resp => {
				if (resp) {
					this.initHrs()
				}
			})
		},
		showPop(hr) {
			this.initAllRoles()
			this.selectedRoles = []
			let roles = hr.roles;
			roles.forEach(role => {
				this.selectedRoles.push(role.id)
			})
		},
		search() {
			this.initHrs()
		},
		deleteHr(hr) {
			this.$confirm('是否删除「' + hr.name + '」，是否继续', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				this.deleteRequest("/system/hr/" + hr.id).then(resp => {
					if (resp) {
						this.initHrs()
					}
				})
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});
		}
	}
}
</script>

<style scoped>
.hr-container {
	margin-top: 10px;
	display: flex;
	flex-wrap: wrap;
	justify-content: space-around;
}

.hr-card {
	width: 380px;
	margin-bottom: 20px;
}

.userface-img {
	width: 72px;
	height: 72px;
	border-radius: 72px;
}

.img-container {
	display: flex;
	justify-content: center;
}

.userinfo-container {
	font-size: 13px;
	color: #0090ff;
}
</style>
