import csv
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import Pipeline

def get_trainig_data(file_path):
    training_data = []
    with open(file_path, newline='', encoding='utf-8') as csvfile:
        csvreader = csv.reader(csvfile, delimiter=';', quotechar='"')
        for row in csvreader:
            if len(row) >= 2:
                training_data.append((row[0], row[1]))
            else:
                print(f"La fila {row} no tiene suficientes columnas.")
    return training_data


def create_default_datamodel():
    # Ejemplo de uso:
    training_data = [
        ("La salud es importante dentro de nuestra empresa", "salud"),
        ("Proporcionamos buenos seguros para nuestros trabajadores", "salud"),
        ("Ofrecemos medicamentos y productos sanitarios asequibles", "salud"),
        ("Donamos los alimentos que sobran en nuestro restaurante", "hambre cero"),
        ("Solo compramos la comida necesaria para no malgastar", "hambre cero"),
        ("Hacemos donaciones de bebidas y alimentos a varias ONG", "hambre cero"),
    ]

    training_data = get_trainig_data("t_model_ods.csv")

    #Creamos el modelo de datos
    model = Pipeline([
        ('vect', CountVectorizer()),
        ('clf', MultinomialNB()),
    ])

    #Entrenamos al modelo con los datos de prueba
    texts = [tup[0] for tup in training_data]
    labels = [tup[1] for tup in training_data]
    model.fit(texts, labels)

    # Predecir la etiqueta para un nuevo texto
    print(model.predict(["Nos gusta ofrecer alimentos a los pobres"])[0])

    return model



def add_single_training_to_datamodel(sentence, label, file_path):
    #Borrar caracteres fatales
    sentence = str.replace(sentence, "\"", "")
    sentence = str.replace(sentence, ";", "")
    label = str.replace(label, "\"", "")
    label = str.replace(label, ";", "")

    # Comprueba si el archivo existe, si no, crea uno nuevo con encabezados de columna
    try:
        with open(file_path, 'r') as f:
            pass
    except FileNotFoundError:
        with open(file_path, 'w', newline='') as f:
            csv_writer = csv.writer(f, delimiter=';')
            csv_writer.writerow([sentence, label])

    # Abre el archivo y añade una nueva fila con los valores de 'sentence' y 'label'
    with open(file_path, 'a', newline='') as f:
        csv_writer = csv.writer(f, delimiter=';')
        csv_writer.writerow([sentence, label])
    
    return get_trainig_data(file_path)



def train_datamodel_sigle_sentence(model, sentence, label):
    # Transformar la frase manualmente
    sentence_transformed = sentence
    for name, transformer in model.steps[:-1]:
        if hasattr(transformer, 'transform'):
            sentence_transformed = transformer.transform([sentence_transformed])

    # Acceder al último paso de la pipeline (el estimador)
    estimator = model.steps[-1][1]

    # Actualizar el modelo con nuevos datos
    estimator.partial_fit(sentence_transformed[0], [label])

    return model


def train_datamodel(training_data):
    training_data = get_trainig_data("t_model_ods.csv")

    #Creamos el modelo de datos
    model = Pipeline([
        ('vect', CountVectorizer()),
        ('clf', MultinomialNB()),
    ])

    #Entrenamos al modelo con los datos de prueba
    texts = [tup[0] for tup in training_data]
    labels = [tup[1] for tup in training_data]
    model.fit(texts, labels)

    return model