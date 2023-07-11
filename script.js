const modalTrigger = document.getElementById('modal-trigger');
const modalContainer = document.getElementById('modal-container');
const registerModalContent = document.getElementById('register-modal-content');
const modalContent = document.getElementById('modal-content');
const modalClose = document.getElementById('modal-close');


function openModal() {
  modalContainer.style.display = 'block';
}


function closeModal() {
  modalContainer.style.display = 'none';
}

function openZooModal(zoo) {

  var modalContainer = document.getElementById('zoo-modal-container');


  var modalName = document.getElementById('zoo-modal-name');
  var modalBiome = document.getElementById('zoo-modal-biome');
  var modalPictures = document.getElementById('zoo-modal-pictures');
  var modalAnimals = document.getElementById('zoo-modal-animals');


  modalName.textContent = zoo.name;
  modalBiome.textContent = 'Biome: ' + zoo.biome;

  modalPictures.innerHTML = '';
  modalAnimals.innerHTML = '';


zoo.pictures.forEach(function(picture) {
  var img = document.createElement('img');
  img.src = picture;
  img.classList.add('modal-image');
  document.getElementById('zoo-modal-pictures').appendChild(img);
});


 zoo.animals.forEach(function(animal) {
  var listItem = document.createElement('li');

 
  var animalInfoDiv = document.createElement('div');
  animalInfoDiv.classList.add('row', 'mb-3');


  var speciesDiv = document.createElement('div');
  speciesDiv.classList.add('col');
  speciesDiv.textContent = 'Species: ' + animal.species;

  var biomeDiv = document.createElement('div');
  biomeDiv.classList.add('col');
  biomeDiv.textContent = 'Biome: ' + animal.biome;

  var dietDiv = document.createElement('div');
  dietDiv.classList.add('col');
  dietDiv.textContent = 'Diet: ' + animal.diet;


  animalInfoDiv.appendChild(speciesDiv);
  animalInfoDiv.appendChild(biomeDiv);
  animalInfoDiv.appendChild(dietDiv);


  var animalImg = document.createElement('img');
  animalImg.src = animal.picture;
  animalImg.classList.add('modal-image', 'mb-3'); 

  
  listItem.appendChild(animalInfoDiv);
  listItem.appendChild(animalImg);


  listItem.classList.add('modal-animal-item', 'mb-3');

  document.getElementById('zoo-modal-animals').appendChild(listItem);
});

  modalContainer.style.display = 'block';
}

function closeZooModal() {
  $("#zoo-modal-container").hide();
}
modalTrigger.addEventListener('click', function(event) {
  event.preventDefault(); 
  
  registerModalContent.style.display = 'none';

  modalContent.style.display = 'block';

  openModal();
});

modalClose.addEventListener('click', closeModal);





//user authenticatio nand registration

var accountData;
let username;

$(document).ready(function() {
  
  $("#submit-button").click(function(event) {
    event.preventDefault(); 
    
    var username = $("#username").val();
    var password = $("#password").val();
   
    var data = {
      username: username,
      password: password
    };
  
    fetch("http://localhost:8080/api/v1/account/authenticate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    })
      .then(response => response.json())
      .then(responseData => {    
       
        $("#username").val("");
        $("#password").val("");
   
        $("#modal-container").hide();
     
        $(".navbar-nav .nav-item:last-child").hide();    
        var myAccountButton = $("<li>")
          .addClass("nav-item")
          .append(
            $("<a>")
              .addClass("nav-link")
              .attr("id", "my-account-button")
              .attr("href", "#my-account-container")
              .text("My Account")
              .click(function() {
                showContainer('myaccountcontainer');
              })
              .click(function() {
                showZooContainer('myzooscontainer');
                $("#zoo-modal-container").hide();
              })
              .click(function() {
                $("#zoo-modal-container").hide();
              })
          );
        $(".navbar:first .navbar-nav").append(myAccountButton);

     
        var logoutButton = $("<li>")
          .addClass("nav-item")
          .append(
            $("<a>")
              .addClass("nav-link")
              .attr("id", "logout-button")
              .text("Logout")
          );
        $(".navbar:first .navbar-nav").append(logoutButton);    
        logoutButton.click(function() {              
          myAccountButton.remove();
          logoutButton.remove();

          $(".navbar:first .nav-item:last-child").show();
          showContainer('about-container');
        });

        fetch("http://localhost:8080/api/v1/account/" + username)
          .then(response => response.json())
          .then(data => {
         
            accountData = data;
            username = data.username;
           
           
          })
          .catch(error => {
            console.error("Failed to fetch account details");
            console.error(error);
          });
      })
      .catch(error => {
       
        console.log("Authentication failed");
        console.error(error);

        var errorMessage = $("<div>")
          .addClass("error-message")
          .text("Incorrect username or password");
        $("#modal-content").append(errorMessage);

        setTimeout(function() {
          errorMessage.remove();
        }, 3000);
      });
  });
});



$(document).ready(function() {
  
  $("#register-link").click(function(event) {
    event.preventDefault();

    $("#modal-content").hide();

    $("#register-modal-content").show();
  });

  $("#register-modal-content form").submit(function(event) {
    event.preventDefault();   
    
    var firstName = $("#register-firstname").val();
    var lastName = $("#register-lastname").val();
    var email = $("#register-email").val();
    var username = $("#register-username").val();
    var password = $("#register-password").val();  


    var data = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      username: username,
      password: password
    };

    fetch("http://localhost:8080/api/v1/account/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    })
      .then(response => response.json())
      .then(responseData => {      
        
        $("#modal-container").hide();   
        $("#modal-content").show();  
        $("#register-firstname").val("");
        $("#register-lastname").val("");
        $("#register-email").val("");
        $("#register-username").val("");
        $("#register-password").val("");
      })
      .catch(error => {
  
        console.log("Registration failed");
        console.error(error);
      });
  });
});
$("#modal-close").click(function() {
  $("#modal-container").hide();
});


$(document).on('click', '#register-link', function() {
  $('#modal-content').hide();
  $('#register-modal-content').show();
});

$(document).on('click', '#login-link', function() {
  $('#register-modal-content').hide();
  $('#modal-content').show();
});




// Miscellaneous Functions

function showContainer(containerId) {
  hideAllContainers();
  document.getElementById(containerId).style.display = "block";
}

function hideAllContainers() {
  var bodyContainers = document.body.getElementsByClassName("container");
  for (var i = 0; i < bodyContainers.length; i++) {
    var container = bodyContainers[i];
    if (!container.closest("header") ) {
      container.style.display = "none";
    }
  }
}

function hideAllZooContainers() {
  var bodyContainers = document.getElementById("myaccountcontainer").getElementsByClassName("container");
  for (var i = 0; i < bodyContainers.length; i++) {
    var container = bodyContainers[i];
    container.style.display = "none";
  }
}

function showZooContainer(containerId) {
  hideAllZooContainers();
  document.getElementById(containerId).style.display = "block";

  if (containerId === "myzooscontainer") {
    loadMyZoosData();
  }
}

window.onload = function() {
  showContainer('about-container');
  
};


$(document).ready(function() {
 
  var interval = 3000;


  $("#galleryCarousel").carousel({
    interval: interval
  });
});


// Zoo-related Functions

var animalList = [];


function loadMyZoosData() {
  var apiUrl = `http://localhost:8080/api/v1/account/getzoo/${accountData.username}`;

  fetch(apiUrl)
    .then(response => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then(zoosData => {
      var zoos = zoosData;

      var myZoosContainer = document.getElementById('myzooscontainer');
      myZoosContainer.innerHTML = '';

      var zooList = document.createElement('ul');

      zoos.forEach(function (zoo) {
        var listItem = document.createElement('li');
        var zooLink = document.createElement('a');
        zooLink.href = '#';
        zooLink.textContent = zoo.name;
        zooLink.addEventListener('click', function () {
          openZooModal(zoo);
        });

        listItem.appendChild(zooLink);
        zooList.appendChild(listItem);
      });

      myZoosContainer.appendChild(zooList);
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    
    });
}

function createZoo() {
  var name = document.getElementById("zoo-name").value;
  var biome = document.getElementById("zoo-biome").value;
  var pictures = document.getElementById("zoo-pictures").value.split(",").map(item => item.trim());
  var animalIds = animalList.map(animal => animal.id);

  var createAZoo = {
    username: accountData.username,
    name: name,
    biome: biome,
    pictures: pictures,
    animals: animalIds
  };

  console.log("Data to be sent:", createAZoo);

  var apiUrl = "http://localhost:8080/api/v1/account/zoo/create";

  fetch(apiUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(createAZoo)
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then(data => {
      

      // Clear input fields
      document.getElementById("zoo-name").value = "";
      document.getElementById("zoo-biome").value = "";
      document.getElementById("zoo-pictures").value = "";

      animalList = [];
      var animalListContainer = document.getElementById("animal-list");
      animalListContainer.innerHTML = "";

      showZooContainer("myzooscontainer");
    })
    .catch(error => {
      console.error("Error:", error);
    });
}


function selectExistingAnimal() {
  var animalInput = document.createElement("div");
  animalInput.innerHTML = `
    <label for="zoo-animals">Animals:</label>
    <input type="text" class="form-control" id="zoo-animals" placeholder="Enter zoo animals (comma-separated)">
    <button onclick="searchAnimals()">Search</button>
  `;
  document.getElementById("animal-info").appendChild(animalInput);
}

function createNewAnimal() {
  var newAnimalFields = document.createElement("div");
  newAnimalFields.id = "new-animal-fields";
  newAnimalFields.innerHTML = `
    <label for="new-species">Species:</label>
    <input type="text" class="form-control" id="new-species" placeholder="Enter species">
    <label for="new-biome">Biome:</label>
    <input type="text" class="form-control" id="new-biome" placeholder="Enter biome">
    <label for="new-diet">Diet:</label>
    <input type="text" class="form-control" id="new-diet" placeholder="Enter diet">
    <label for="new-picture">Picture:</label>
    <input type="text" class="form-control" id="new-picture" placeholder="Enter picture URL (if the link is too long it wont work)">
    <button  class="my-3" onclick="submitNewAnimal()">Create New Animal</button>
  `;

  document.getElementById("animal-info").appendChild(newAnimalFields);
}


function submitNewAnimal() {
  var newSpecies = document.getElementById("new-species").value;
  var newBiome = document.getElementById("new-biome").value;
  var newDiet = document.getElementById("new-diet").value;
  var newPicture = document.getElementById("new-picture").value;

  var newAnimal = {
    species: newSpecies,
    biome: newBiome,
    diet: newDiet,
    picture: newPicture
  };

 
  var apiUrl = "http://localhost:8080/api/v1/animals/create";

  fetch(apiUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(newAnimal)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return response.json();
  })
  .then(data => {
    displayAnimalInfo(data);

    
    var newAnimalFields = document.getElementById("new-animal-fields");
    newAnimalFields.style.display = "none";
  })
  .catch(error => {
    console.error("Error:", error);
    var animalInfoContainer = document.getElementById("animal-info");
    animalInfoContainer.innerHTML = "Failed to create a new animal.";

    setTimeout(() => {
      animalInfoContainer.innerHTML = "";
    }, 3000);
  });
}




function searchAnimals() {
  var speciesInput = document.getElementById("zoo-animals").value;
  var apiUrl = "http://localhost:8080/api/v1/animals/" + speciesInput;
  var animalInfoContainer = document.getElementById("animal-info");

  fetch(apiUrl)
    .then(response => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then(data => {
      displayAnimalInfo(data);

      animalInfoContainer.innerHTML = "";
    })
    .catch(error => {
      console.error("Error:", error);
      animalInfoContainer.innerHTML = "Failed to fetch animals.";


      setTimeout(() => {
        animalInfoContainer.innerHTML = "";
      }, 3000);
    });
}

function deleteZoo() {
  const zooNameElement = document.getElementById("zoo-modal-name");
  const zooName = zooNameElement.textContent; 
  
  fetch("http://localhost:8080/api/v1/account/zoo/delete", {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      username: accountData.username,
      name: zooName,
    }),
  })
    .then((response) => {
      if (response.ok) {
       
        return response.text(); 
      } else {
        throw new Error("Failed to delete zoo");
      }
    })
    .then((data) => {         
      showZooContainer('myzooscontainer');
      $("#zoo-modal-container").hide();

    })
    .catch((error) => {     
      console.error("Error deleting zoo:", error);
    });
}


function displayAnimalInfo(animal) {
  var animalDiv = document.createElement("div");
  var animalListContainer = document.getElementById("animal-list");

  if (!animal) {
    animalDiv.innerHTML = "No animal found.";


    setTimeout(() => {
      animalDiv.innerHTML = "";
    }, 3000);
  } else {
  
    animalList.push(animal);

    animalDiv.innerHTML = `
      <p>Species: ${animal.species}</p>
      <p>Biome: ${animal.biome}</p>
      <p>Diet: ${animal.diet}</p>
      <p>Picture: ${animal.picture}</p>
    `;
  }

  animalListContainer.appendChild(animalDiv);
}







function loadAccountData() {
  document.getElementById("firstName").value = accountData.firstName;
  document.getElementById("lastName").value = accountData.lastName;
  document.getElementById("usernameUpdate").value = accountData.username;
  document.getElementById("email").value = accountData.email;

 
}

document.getElementById('deleteAccountButton').addEventListener('click', function() {
  var apiUrl = `http://localhost:8080/api/v1/account/${accountData.username}`;

  fetch(apiUrl, {
    method: 'DELETE',
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
     
     location.reload();

 
  })
  .catch(error => {
    console.error('Error deleting account:', error);
    
  });
});


function updateHiddenPassword() {
  var visiblePassword = document.getElementById("visiblePassword").value;
  document.getElementById("updatepassword").value = visiblePassword;
}


function updateInfo() {
  showZooContainer('updateAccountContainer');
  loadAccountData();
}



$("#updateButton").click(function(event) {
  event.preventDefault(); 
  var updatedFirstName = $("#firstName").val();
  var updatedLastName = $("#lastName").val();
  var updatedEmail = $("#email").val(); 
  var updatedUsername = $("#usernameUpdate").val();
  var updatedPassword = $("#visiblePassword").val();

  var updatedData = {
    firstName: updatedFirstName,
    lastName: updatedLastName,
    email: updatedEmail,
    username: updatedUsername,
    password: updatedPassword
  }; 

  fetch("http://localhost:8080/api/v1/account", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(updatedData)
  })
    .then(response => response.json())
    .then(responseData => {
 

     
      fetch("http://localhost:8080/api/v1/account/" + updatedData.username)
        .then(response => response.json())
        .then(updatedAccountData => {
        
          accountData = updatedAccountData;


          
          showZooContainer('myzooscontainer');

          
        })
        .catch(error => {
          console.log("Failed to fetch updated account data");
          console.error(error);
        });
    })
    .catch(error => {
      console.log("Account update failed");
      console.error(error);
    });
});







function loadAnimals() {
  fetch("http://localhost:8080/api/v1/animals")
    .then((response) => response.json())
    .then((data) => {
      const jumbotron = document.getElementById("animals-jumbotron");

     
      jumbotron.innerHTML = '';

      data.forEach((animal) => {
        const card = document.createElement("div");
        card.className = "animal-card";
        card.innerHTML = `
          <img class="animal-image" src="${animal.picture}" alt="${animal.species}">
          <h4 class="animal-name">${animal.species}</h4>`;
        currentMenu=false;
       
        card.addEventListener("click", () => {
          
          if (currentMenu) {
            currentMenu.remove();
          }

          const menuTemplate = document.getElementById("animal-details-template");
          const menu = menuTemplate.cloneNode(true);
          menu.className = "animal-details-menu";
          menu.querySelector(".modal-animal-name").textContent = animal.species;
          menu.querySelector(".modal-animal-species").textContent = `Species: ${animal.species}`;
          menu.querySelector(".modal-animal-biome").textContent = `Biome: ${animal.biome}`;
          menu.querySelector(".modal-animal-diet").textContent = `Diet: ${animal.diet}`;
          menu.querySelector(".modal-animal-image").src = animal.picture;

        
          const closeButton = menu.querySelector(".close-button");
          closeButton.addEventListener("click", () => {
            menu.remove();
          });

   
          card.after(menu);
          menu.style.display = "block";

         
          currentMenu = menu;
        });

        jumbotron.appendChild(card);
      });
    })
    .catch((error) => {
      console.error("Failed to fetch animal data");
      console.error(error);
    });
}








function animalsPressed(){

  showContainer('animals-container');

  loadAnimals();
}