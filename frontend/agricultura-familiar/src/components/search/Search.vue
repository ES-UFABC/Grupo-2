<template>
  <b-input-group class="search">
    <b-input-group-prepend>
      <button class="input-group-text" @click="pesquisar">
        <img src="../../../src/assets/search.svg" alt="Muda">
      </button>
    </b-input-group-prepend>

    <b-form-input
      class="SearchInput"
      size="lg"
      placeholder="Busque um produtor"
      list="historico"
      v-model="term"
      autocomplete="off"
      ref="search"
      debounce="1500"
      @focus.prevent="carregarHistorico"
      @contextmenu="limparHistorico"
    >
    </b-form-input>

    <b-input-group-append>
      <button class="input-group-text" @click="abrirGeolocalizacao">
        <img src="../../../src/assets/location.svg" alt="Location">
      </button>
    </b-input-group-append>

    <datalist id="historico">
      <option v-for="item in history" :key="item">{{ item }}</option>
    </datalist>
  </b-input-group>
</template>

<script>
import SearchHistoryService from '../../services/search-history-service'
export default {
  data() {
    return {
      term: "",
      history: [],
    };
  },
  methods: {
    pesquisar() {
      this.$emit('pesquisar', this.term)
      if(this.term)
        this.$store.dispatch('search/setTerm', this.term)
      else
        this.$store.dispatch('search/cleanTerm')

      SearchHistoryService.salvarPesquisaNoHistoricoDeBuscas(this.term)
    },
    abrirGeolocalizacao() {
      this.$emit('abrirGeolocalizacao')
    },
    carregarHistorico() {
      let self = this;
      SearchHistoryService.carregarHistoricoDeBuscas()
        .then(historico => {
          self.history.length = 0;
          self.history = historico.termos
        })

      if (!['/painel/consumidor', '/painel/consumidor/maps'].includes(self.$route.path))
        self.$router.push('/painel/consumidor')
    },
    limparHistorico() {
      SearchHistoryService.limparHistoricoDeBuscas()
        .then(cleaned => {
          if (cleaned)
              console.log('Historico apagado...')
        })
    }
  },
  watch: {
    term: function (newTerm, oldTerm) {
      this.pesquisar()
    }
  }
};
</script>

<style scoped>
.input-group-text {
  width: 50px;
  border: none;
  background-color: #f8f8f8;
  display: flex;
  align-items: center;
  justify-content: center;
}
.SearchInput {
  background-color: #f8f8f8;
}
.input-group-text img {
  height: 26px;
}
.SearchInput {
  border: none;
}

/* select {
  -webkit-appearance: none;
  -moz-appearance: none;
  -o-appearance: none;
  -ms-appearance: none;
  appearance: none;
} */
</style>
