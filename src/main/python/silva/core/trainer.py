from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import Pipeline

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


def train_datamodel(model, training_data):
    # Dividir los datos en textos y etiquetas
    texts = [tup[0] for tup in training_data]
    labels = [tup[1] for tup in training_data]

    # Acceder al último paso de la pipeline (el estimador)
    estimator = model.steps[-1][1]

    # Entrenar el modelo con los datos de entrenamiento
    estimator.partial_fit(texts, labels)

    return model