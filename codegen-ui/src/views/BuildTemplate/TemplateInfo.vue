<template>
  <div>
    <el-card>
      <div slot="header">
        <span class="title">模板信息</span>
      </div>
      <div>
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-form-item label="模板名称" prop="groupName">
            <el-input v-model="form.groupName" />
          </el-form-item>
          <el-form-item label="介绍" prop="description">
            <el-input v-model="form.description" type="textarea" />
          </el-form-item>
          <el-form-item label="图片">
            <el-upload ref="upload" class="upload" action="#" list-type="picture-card" :file-list="fileList" :auto-upload="false">
              <i slot="default" class="el-icon-plus" />
              <div slot="file" slot-scope="{file}">
                <el-image style="width: 100%; height: 100%" :src="file.url" fit="cover" />
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                    <i class="el-icon-zoom-in" />
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleDownload(file)"
                  >
                    <i class="el-icon-download" />
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleRemove(file)"
                  >
                    <i class="el-icon-delete" />
                  </span>
                </span>
              </div>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <el-dialog :visible.sync="previewOpen">
      <img width="100%" :src="imageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import { arrayRemove } from '@/utils'

export default {
  name: 'TemplateInfo',
  data() {
    return {
      imageUrl: '',
      previewOpen: false,
      disabled: false,
      fileList: [],
      form: {
        groupName: '',
        description: ''
      },
      rules: {
        groupName: [
          { required: true, message: '请输入模板名称', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入模板介绍', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleRemove(file) {
      arrayRemove(file, this.$refs.upload.uploadFiles)
    },
    handlePictureCardPreview(file) {
      this.imageUrl = file.url
      this.previewOpen = true
    },
    handleDownload(file) {
      console.log(file)
    },
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    cancel() {
      this.$refs['form'].resetFields()
    }
  }
}
</script>

<style lang="scss" scoped>
.title {
  font-weight: bold;
}
.upload {
  ::v-deep .el-upload-list__item {
    & > div {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
