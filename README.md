# PokeDex Android App 🎉

Este é um aplicativo nativo Android em **Kotlin** que consome a **PokeAPI** para listar Pokémons, buscar por nome e exibir detalhes completos de cada criatura.  
O projeto foi construído com foco em:

- Arquitetura **MVVM**  
- Injeção de dependência com **Koin**  
- Paginação eficiente com **Paging 3**  
- Experiência fluida usando **Coroutines**, **Flow** e **ViewBinding**  

---

## 📌 Tecnologias Utilizadas

- **Linguagem**: Kotlin  
- **Arquitetura**: MVVM (Model-View-ViewModel)  
- **DI**: Koin  
- **HTTP**: Retrofit2 + OkHttp (com cache offline)  
- **Paginação**: Android Paging 3  
- **Concorrência**: Kotlin Coroutines + Flow  
- **UI**: RecyclerView (grid 2×2), ConstraintLayout, CardView, Material Components  
- **Imagens**: Glide  
- **Persistência (futura)**: Room (favoritos offline)  
- **Controle de Versão**: Git & GitHub  

---

## 🔥 Funcionalidades

- ✅ **Lista paginada** de Pokémons em grid 2×2  
- ✅ **Busca em tempo real** por nome de Pokémon  
- ✅ **Tela de detalhes**: imagem oficial, nome, altura, peso e tipos  
- ✅ **Cache HTTP**: até 7 dias offline usando OkHttp  
- ✅ **Loading state**: ProgressBar enquanto carrega nova página  
- ✅ **Tratamento de erros**: mensagens de falha exibidas ao usuário  
- ✅ **Injeção de Dependências**: módulos Koin para Retrofit, ViewModel, Repository  

---

## 🎯 Como Executar o Projeto

1. **Clone** este repositório  
   ```bash
   git clone https://github.com/lfmdsant/pokedex-android.git
   cd pokedex-android
Lista (Grid)	Busca em Ação	Detalhes

🛠️ Melhorias Futuras

🔹 Favoritos persistidos com Room + LiveData

🔹 Dark Mode automático seguindo tema do sistema

🔹 Testes unitários (ViewModel) e instrumentados (UI)

🔹 Transições compartilhadas lista ↔ detalhe

🔹 Animações sutis para carregamento de cards

🤝 Contribuições

Faça ⭐ este projeto

Fork

Crie sua branch (git checkout -b feature/nome-da-feature)

Commit suas mudanças (git commit -m 'Feature: descrição')

Push na branch (git push origin feature/nome-da-feature)

Abra um Pull Request 🚀

📜 Licença


Este projeto está licenciado sob a MIT License — sinta-se livre para usar, modificar e distribuir.


Feito com 💛 por Lucas Santos
