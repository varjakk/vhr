<template>
	<div>
		<div class="permissionInput">
			<el-input v-model="role.name" placeholder="请输入角色英文名" size="small">
				<template slot="prepend">ROLE_</template>
			</el-input>
			<el-input v-model="role.nameZh" placeholder="请输入" size="small" @keydown.enter.native="addRole"></el-input>
			<el-button icon="el-icon-plus" size="small" type="primary" @click="addRole">添加角色</el-button>
		</div>
		<div class="permissionMain">
			<el-collapse v-model="activeName" accordion @change="changeCollapse">
				<el-collapse-item v-for="(item, index) in roles" :key="index" :name="item.id" :title="item.nameZh">
					<el-card class="box-card">
						<div slot="header" class="clearfix">
							<span>可访问的资源</span>
							<el-button icon="el-icon-delete" style="float: right; padding: 3px 0; color: red"
							           type="text" @click="deleteRole(item)"></el-button>
						</div>
						<div>
							<el-tree
								:key=index
								ref="tree"
								:data="allMenus"
								:default-checked-keys="selectedMenus"
								:props="props"
								node-key="id"
								show-checkbox>
							</el-tree>
							<div style="display: flex; justify-content: flex-end">
								<el-button>取消修改</el-button>
								<el-button type="primary" @click="handleUpdate(item.id,index)">确认修改</el-button>
							</div>
						</div>
					</el-card>
				</el-collapse-item>
			</el-collapse>
		</div>
	</div>
</template>

<script>
export default {
	name: "PermissionMana",
	data() {
		return {
			role: {
				name: '',
				nameZh: '',
			},
			roles: [],
			allMenus: [],
			selectedMenus: [],
			props: {
				label: 'name',
				children: 'children'
			},
			activeName: -1,
		}
	},
	mounted() {
		this.initRoles();
	},
	methods: {
		initRoles() {
			this.getRequest("/system/basic/permission/").then(resp => {
				if (resp) {
					this.roles = resp
				}
			})
		},
		changeCollapse(rid) {
			if (rid) {
				this.initAllMenus()
				this.initSelectedMenus(rid)
			}
		},
		initAllMenus() {
			this.getRequest("/system/basic/permission/menus/").then(resp => {
				if (resp) {
					this.allMenus = resp
				}
			})
		},
		initSelectedMenus(rid) {
			this.getRequest("/system/basic/permission/mids/" + rid).then(resp => {
				if (resp) {
					this.selectedMenus = resp
				}
			})
		},
		addRole() {
			if (this.role.name && this.role.nameZh) {
				this.postRequest("/system/basic/permission/role", this.role).then(resp => {
					if (resp) {
						this.initRoles()
						this.role = {}
					}
				})
			} else {
				this.$message.error("角色名不可为空");
			}
		},
		deleteRole(role) {
			this.$confirm('是否删除【' + role.nameZh + '】角色？', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				this.deleteRequest("/system/basic/permission/role/" + role.id).then(resp => {
					if (resp) {
						this.initRoles()
					}
				})
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});
		},
		handleUpdate(rid, index) {
			let tree = this.$refs.tree[index];
			let checkedKeys = tree.getCheckedKeys(true);
			let url = '/system/basic/permission/?rid=' + rid;
			checkedKeys.forEach(key => {
					url += '&mids=' + key;
				}
			)
			this.putRequest(url).then(resp => {
				if (resp) {
					this.activeName = -1;//更新完成后收起
				}
			})
		},
		filterNode(value, data) {
			if (!value) return true;
			return data.name.indexOf(value) !== -1;
		}
	}
}
</script>

<style>
.permissionInput {
	display: flex;

}

.permissionInput .el-input {
	width: 300px;
	margin-right: 5px;
}

.permissionMain {
	margin-top: 10px;
	width: 60%;
}
</style>
