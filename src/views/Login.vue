<template>
	<div>
		<el-form ref="loginForm" :model="loginForm" :rules="rules" class="loginContainer">
			<h3 class="loginTitle">用户登录</h3>
			<el-form-item prop="username">
				<el-input v-model="loginForm.username" auto-complete="off" placeholder="账号" type="text"></el-input>
			</el-form-item>
			<el-form-item prop="password">
				<el-input v-model="loginForm.password" auto-complete="off" placeholder="密码" type="password"
				          @keydown.enter.native="submitLogin"></el-input>
			</el-form-item>
			<el-checkbox v-model="checked" class="loginCheck">记住密码</el-checkbox>
			<el-form-item>
				<el-button style="width:100%" type="primary" @click="submitLogin">登录</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
export default {
	name: "Login",
	data() {
		return {
			loginForm: {
				username: "admin",
				password: 123,
			},
			checked: true,
			rules: {
				account: [{required: true, message: "请输入用户名", trigger: "blur"}],
				checkPass: [{required: true, message: "请输入密码", trigger: "blur"}],
			}
		}
	},
	methods: {
		submitLogin() {
			// alert("hello")
			this.$refs.loginForm.validate((valid) => {
				if (valid) {
					this.postKeyValueRequest('/doLogin', this.loginForm).then(resp => {
						if (resp) {
							window.sessionStorage.setItem("user", JSON.stringify(resp.obj))
							let path = this.$route.query.redirect;
							//replace指替换页面
							this.$router.replace((path === "/home" || path === undefined) ? "/home" : path)
						}
					})
				} else {
					this.$message.error('请输入所有的文本框');
					return false;
				}
			});
		}
	}
}
</script>

<style>
.loginContainer {
	border-radius: 15px;
	background-clip: padding-box;
	margin: 180px auto;
	width: 350px;
	padding: 35px 35px 15px 35px;
	/*background: #fff;*/
	border: 1px solid #eaeaea;
	box-shadow: 0 0 25px #cac6c6;
}

.loginTitle {
	margin: 0 auto 40px auto;
	text-align: center;
	color: #505458;
}

.loginCheck {
	margin: 0 0 35px auto;
	text-align: left;
}

</style>