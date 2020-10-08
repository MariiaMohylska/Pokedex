package com.android.example.thepokedex

data class Pokemon (val id: String, val name: String, val imgUrl:String)

val pokemonList = listOf(
    Pokemon("1","Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
    Pokemon("4","Charmander", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"),
    Pokemon("18","Pidgeot", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/18.png"),
    Pokemon("25","Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"),
    Pokemon("54","Psyduck", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/54.png"),
    Pokemon("7","Squirtle", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png"),
    Pokemon("12","Butterfree", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/12.png"),
    Pokemon("10","Caterpie", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png"),
    Pokemon("35","Clefairy", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/35.png"),
    Pokemon("77","Ponyta", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/77.png"),
    Pokemon("55","Persian", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/55.png"),
    Pokemon("48","Venonat", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/48.png"),
    Pokemon("66","Machop", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/66.png"),
    Pokemon("59","Arcanine", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/59.png"),
    Pokemon("666","Vivillon", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/666.png")
)