import os;
import sys;
from trainer import create_default_datamodel, train_datamodel_sigle_sentence;
from joblib import dump, load;

MODEL_FILE = 's1lv4_model_one.joblib'

def get_datamodel():

    if os.path.exists(MODEL_FILE):
        # Cargar el modelo
        return load(MODEL_FILE)
    else:
        model = create_default_datamodel()
        # Guardar el modelo en un archivo
        dump(model, MODEL_FILE)
        return model


def train_model(sentence, label):

    model = get_datamodel()
    #model = train_datamodel_sigle_sentence(model, sentence, label)
    #dump(model, MODEL_FILE)
    print("model saved")


def model_predict(sentence):
    model = get_datamodel()
    print(model.predict([sentence])[0])

'''
    MAIN SCRIPT
'''
if __name__ == "__main__":
    # Obtener el primer argumento pasado desde la línea de comandos
    #try:
    command = sys.argv[1]

    # Llamar al entrenador
    if (command == "-trainer" or command == "-t"):
        sentence = sys.argv[2]
        label = sys.argv[3]
        train_model(sentence, label)

    # Llamar al predictor
    if (command == "-predict" or command == "-p"):
        sentence = sys.argv[2]
        model_predict(sentence)

   #except IndexError:
   #    print("Not all arguments are introduced")