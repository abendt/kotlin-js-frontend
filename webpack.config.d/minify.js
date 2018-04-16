if (defined.PRODUCTION) {
    console.log("Production build is enabled!");

    defined['process.env.NODE_ENV'] = JSON.stringify('production')

    config.mode = "production"
}