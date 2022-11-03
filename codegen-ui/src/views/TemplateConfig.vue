<template>
  <div>
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="68px">
      <el-form-item label="用户名称" prop="userName">
        <el-input
          v-model="queryParams.type"
          placeholder="请输入用户名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row class="toolbar clear" :gutter="10" style="margin-bottom: 5px;">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <div class="righttoolbar right clear">
        <el-slider v-model="width" :format-tooltip="formatTooltip" :min="200" :max="300" />
        <el-button icon="el-icon-s-tools" circle />
      </div>
    </el-row>

    <div class="waterfall">
      <div v-for="i in 10" :key="'b'+i" class="item" :class="{'is-checked': isChecked(i)}" :style="{width: width + 'px'}">
        <img :src="require('@/assets/logo.png')" alt="">
        <div>
          <div class="title">tkMybatis三层架构代码生成</div>
          <div class="content">福娃链接可阿萨德阿斯顿法师法师分解拉三暗示法发顺丰大是否大是大非季度福利卡聚少离多咖啡机阿萨德</div>
          <div class="tags">
            <el-tag>Tag 1</el-tag>
            <el-tag type="success">Tag 2</el-tag>
            <el-tag type="info">Tag 3</el-tag>
            <el-tag type="warning">Tag 4</el-tag>
            <el-tag type="danger">Tag 5</el-tag>
          </div>
          <div class="operate clear">
            <div class="right">
              <i class="el-icon-edit" @click="handleUpdate(i)" />
              <i class="el-icon-delete" @click="handleDelete(i)" />
              <span class="my-radio" :class="{'is-checked': isChecked(i)}" @click="radioChange(i)" />
            </div>
          </div>
        </div>
      </div>
      <i v-for="i in 7" :key="i" class="placeholder" :style="{width: width + 'px'}" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'TemplateConfig',
  data() {
    return {
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        type: undefined
      },
      // 默认宽度
      width: 260
    }
  },
  methods: {
    handleQuery() {},
    resetQuery() {},
    handleAdd() {
      this.$router.push('/buildTemplate')
    },
    handleDelete() {},
    handleUpdate() {},
    handleImport() {},
    handleExport() {},
    formatTooltip(val) {
      return `${val}px`
    },
    radioChange(val) {
      if (this.isChecked(val)) {
        this.ids.splice(this.ids.indexOf(val), 1)
      } else {
        this.ids.push(val)
      }
      this.single = this.ids.length !== 1
      this.multiple = !this.ids.length
    },
    isChecked(id) {
      return this.ids.includes(id)
    }
  }
}
</script>

<style lang="scss" scoped>
.righttoolbar {
  display: flex;
  .desc {
    font-size: 15px;
    color: #8492a6;
    line-height: 39px;
    margin-right: 15px;
  }
  .el-slider {
    display: inline-block;
    width: 200px;
    margin-right: 24px;
  }
}
.waterfall {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: baseline;
  .item {
    box-sizing: border-box;
    display: inline-block;
    margin: 0 18px 18px 0;
    border-radius: 8px;
    border: 1px solid #fff;
    overflow: hidden;
    background-color: #fff;
    box-shadow: 0 0 10px #eeeeee;
    transition: box-shadow 0.2s linear;

    img {
      width: 100%;
    }
    &.is-checked {
      box-shadow: 0 0 10px #409eff;
    }
    & > div {
      padding: 8px 10px;
      .title {
        margin-bottom: 8px;
        font-weight: bold;
      }
      .content {
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 4;
        -webkit-box-orient: vertical;
        margin-bottom: 8px;
      }
      .tags {
        .el-tag {
          height: 22px;
          line-height: 20px;
          margin: 5px 4px 0 0;
        }
      }
      .operate {
        span, i {
          margin: 4px 0 0 8px;
          cursor: pointer;
        }
      }
    }
  }
  i.placeholder {
    margin: 0 18px 18px 0;
  }
}
</style>
